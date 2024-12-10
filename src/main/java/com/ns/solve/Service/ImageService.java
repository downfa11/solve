package com.ns.solve.Service;

import com.ns.solve.Domain.AssignmentDto;
import com.ns.solve.Domain.Case;
import com.ns.solve.Repository.ProblemRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cloudwatchlogs.CloudWatchLogsClient;
import software.amazon.awssdk.services.cloudwatchlogs.model.CloudWatchLogsException;
import software.amazon.awssdk.services.cloudwatchlogs.model.GetLogEventsRequest;
import software.amazon.awssdk.services.cloudwatchlogs.model.GetLogEventsResponse;
import software.amazon.awssdk.services.ecr.EcrClient;
import software.amazon.awssdk.services.ecr.model.BatchDeleteImageRequest;
import software.amazon.awssdk.services.ecr.model.EcrException;
import software.amazon.awssdk.services.ecr.model.ImageIdentifier;
import software.amazon.awssdk.services.ecs.EcsClient;
import software.amazon.awssdk.services.ecs.model.AssignPublicIp;
import software.amazon.awssdk.services.ecs.model.AwsVpcConfiguration;
import software.amazon.awssdk.services.ecs.model.ContainerOverride;
import software.amazon.awssdk.services.ecs.model.KeyValuePair;
import software.amazon.awssdk.services.ecs.model.LaunchType;
import software.amazon.awssdk.services.ecs.model.NetworkConfiguration;
import software.amazon.awssdk.services.ecs.model.RunTaskRequest;
import software.amazon.awssdk.services.ecs.model.RunTaskResponse;
import software.amazon.awssdk.services.ecs.model.TaskOverride;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ProblemRepository problemRepository;

    public void process(AssignmentDto assignmentDto){

        // 해당 problemId에 맞는 케이스를 DB에서 가져온다.
        List<Case> problemCases = problemRepository.findProblemCases(assignmentDto.getProblemId());

        try {
            // Git Repository Clone
            String localPath = "/tmp/" + assignmentDto.getMembershipId();
            cloneRepository(assignmentDto.getGitDirectory(), localPath);

            // ECS Image Push
            String imageName = "assignment-" + assignmentDto.getMembershipId();
            String ecrUri = "your-ecr-uri";
            buildAndPushDockerImage(localPath, imageName, ecrUri);

            // Image Execute
            String clusterName = "your-cluster-name";
            String taskDefinition = "your-task-definition";
            String containerName = "your-container-name";

            runTask(clusterName, taskDefinition, containerName, ecrUri, testInput);

            for(Case problemCase : problemCases) {
                String expectedOutput = problemCase.getExpectedOutput();
                String result = InputCaseTask(problemCase.getInput());

                if (result == null) {
                    System.out.println("result is null");
                    return;
                }

                if (result.equals(expectedOutput)) {
                    System.out.println("Test Passed! Output: " + result);
                } else {
                    System.out.println("Test Failed. Expected: " + expectedOutput + ", but got: " + result);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error processing assignment: " + e.getMessage());
        }
    }

    public void cloneRepository(String repoUrl, String localPath) throws GitAPIException, IOException {
        File repoDir = new File(localPath);
        if (!repoDir.exists()) {
            Git.cloneRepository()
                    .setURI(repoUrl)
                    .setDirectory(repoDir)
                    .call();

            System.out.println("Repository cloned successfully.");
        } else {
            System.out.println("Repository already exists at " + localPath);
        }
    }

    public void buildAndPushDockerImage(String repoPath, String imageName, String ecrUri) {
        try {
            ProcessBuilder builder = new ProcessBuilder("docker", "build", "-t", imageName, repoPath);
            builder.inheritIO().start().waitFor();

            ProcessBuilder loginBuilder = new ProcessBuilder(
                    "aws", "ecr", "get-login-password", "--region", "your-region",
                    "|", "docker", "login", "--username", "AWS", "--password-stdin", ecrUri
            );
            loginBuilder.inheritIO().start().waitFor();

            ProcessBuilder pushBuilder = new ProcessBuilder(
                    "docker", "tag", imageName, ecrUri + "/" + imageName,
                    "&&", "docker", "push", ecrUri + "/" + imageName
            );
            pushBuilder.inheritIO().start().waitFor();

            deleteDirectory(new File(repoPath));

            System.out.println("Docker image pushed to ECR successfully.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void deleteDirectory(File file) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                deleteDirectory(subFile);
            }
        }
        file.delete();
    }

    public String runTaskWithInput(String clusterName, String taskDefinition, String containerName, String ecrUri, String jsonInput) {
        try (EcsClient ecsClient = EcsClient.create()) {
            RunTaskRequest request = RunTaskRequest.builder()
                    .cluster(clusterName)
                    .taskDefinition(taskDefinition)
                    .launchType(LaunchType.FARGATE)
                    .overrides(TaskOverride.builder()
                            .containerOverrides(ContainerOverride.builder()
                                    .name(containerName)
                                    .environment(KeyValuePair.builder()
                                            .name("TEST_CASES_JSON")
                                            .value(jsonInput)
                                            .build())
                                    .build())
                            .build())
                    .networkConfiguration(NetworkConfiguration.builder()
                            .awsvpcConfiguration(AwsVpcConfiguration.builder()
                                    .subnets("subnet-xxxxxxxx")
                                    .securityGroups("sg-xxxxxxxx")
                                    .assignPublicIp(AssignPublicIp.ENABLED)
                                    .build())
                            .build())
                    .build();

            RunTaskResponse response = ecsClient.runTask(request);
            if (response.tasks().isEmpty()) {
                throw new RuntimeException("Failed to start ECS task.");
            } else {
                System.out.println("ECS task started successfully.");
            }

            String logOutput = getLogsFromCloudWatch("ecs-logs", containerName);
            System.out.println("Log Output: " + logOutput);

            deleteEcrImage("image-name");

            return logOutput;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String getLogsFromCloudWatch(String logGroupName, String logStreamName) {
        try (CloudWatchLogsClient client = CloudWatchLogsClient.create()) {
            GetLogEventsRequest request = GetLogEventsRequest.builder()
                    .logGroupName(logGroupName)
                    .logStreamName(logStreamName)
                    .startFromHead(true) // 처음부터 읽기
                    .build();

            GetLogEventsResponse response = client.getLogEvents(request);
            System.out.println("Raw Events: " + response.events());

            return response.events().stream()
                    .map(event -> event.toString())
                    .collect(Collectors.joining("\n"));
        } catch (CloudWatchLogsException e) {
            e.printStackTrace();
            return "Error fetching logs: " + e.getMessage();
        }
    }

    private void deleteEcrImage(String imageName) {
        try (EcrClient ecrClient = EcrClient.create()) {
            BatchDeleteImageRequest deleteRequest = BatchDeleteImageRequest.builder()
                    .repositoryName(imageName)
                    .imageIds(ImageIdentifier.builder().imageTag("latest").build())
                    .build();

            ecrClient.batchDeleteImage(deleteRequest);
            System.out.println("ECR image deleted successfully.");
        } catch (EcrException e) {
            e.printStackTrace();
        }
    }

}
