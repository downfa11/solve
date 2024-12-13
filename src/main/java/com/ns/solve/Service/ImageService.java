package com.ns.solve.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.ns.solve.Domain.dto.Case;
import com.ns.solve.Domain.dto.AssignmentDto;
import com.ns.solve.Repository.ProblemRepository;
import java.io.File;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ecr.EcrClient;
import software.amazon.awssdk.services.ecr.model.BatchDeleteImageRequest;
import software.amazon.awssdk.services.ecr.model.EcrException;
import software.amazon.awssdk.services.ecr.model.ImageIdentifier;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final DockerClient dockerClient = DockerClientBuilder.getInstance().build();;
    private final ProblemRepository problemRepository;

    public void process(AssignmentDto assignmentDto){

        // 해당 problemId에 맞는 케이스를 DB에서 가져온다.
        Long problemId = assignmentDto.getProblemId();
        List<Case> problemCases = problemRepository.findCaseByProblemId(problemId);

        try {
            // Git Repository Clone
            String localPath = "/tmp/" + assignmentDto.getMembershipId();
            cloneRepository(assignmentDto.getGitDirectory(), localPath);

            // ECS Image Push
            String imageName = "assignment-" + assignmentDto.getMembershipId();
            String ecrUri = "your-ecr-uri";
            buildImage(localPath, imageName, ecrUri);

            // Image Execute
            String clusterName = "your-cluster-name";
            String taskDefinition = "your-task-definition";
            String containerName = "your-container-name";

            // runTask(clusterName, taskDefinition, containerName, ecrUri, testInput);

            for(Case problemCase : problemCases) {
                String expectedOutput = problemCase.getExpectedOutput();
                String result = null; // InputCaseTask(problemCase.getInput());

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

    public void cloneRepository(String repoUrl, String localPath) throws GitAPIException {
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

    public void buildImage(String repoPath, String imageName, String ecrUri) {
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


    public String buildCodeImage(String language, String codePath, String imageName) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "docker", "build", "-t", imageName, codePath
            );
            Process process = pb.start();
            process.waitFor();
            return "Docker image built: " + imageName;
        } catch (Exception e) {
            return "Error building Docker image: " + e.getMessage();
        }
    }

    public String buildDockerImage(String dockerfilePath, String imageName) {
        try {
            File dockerfileDir = new File(dockerfilePath);

            dockerClient.buildImageCmd(dockerfileDir)
                    .withTag(imageName)
                    .exec(new BuildImageResultCallback())
                    .awaitCompletion();

            return "Docker image " + imageName + " built successfully!";
        } catch (Exception e) {
            return "Error building Docker image: " + e.getMessage();
        }
    }

    public void pullImage(String imageName) throws InterruptedException {
        dockerClient.pullImageCmd(imageName).exec(new ResultCallback.Adapter<PullResponseItem>() {
            @Override
            public void onNext(PullResponseItem item) {
                System.out.println(item.getStatus());
            }
        }).awaitCompletion();
    }

    public String createContainer(String imageName, String... command) {
        CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                .withCmd(command)
                .withHostConfig(HostConfig.newHostConfig())
                .exec();

        return container.getId();
    }


    public void startContainer(String containerId) {
        dockerClient.startContainerCmd(containerId).exec();
    }


    public String getContainerLogs(String containerId) throws InterruptedException {
        StringBuilder logs = new StringBuilder();
        dockerClient.logContainerCmd(containerId)
                .withStdOut(true)
                .withStdErr(true)
                .exec(new ResultCallback.Adapter<Frame>() {
                    @Override
                    public void onNext(Frame frame) {
                        logs.append(new String(frame.getPayload()));
                    }
                }).awaitCompletion();
        return logs.toString();
    }

    public String buildDockerImage(String language, String projectDir, String imageName) {
        String dockerfilePath = getDockerfilePath(language);

        if (dockerfilePath == null)
            return "Unsupported language: " + language;

        try {
            File dockerfileDir = new File(projectDir);

            dockerClient.buildImageCmd(dockerfileDir)
                    .withTag(imageName)
                    .exec(new BuildImageResultCallback())
                    .awaitCompletion();

            return "Docker image " + imageName + " built successfully!";
        } catch (Exception e) {
            return "Error building Docker image: " + e.getMessage();
        }
    }

    // 언어에 맞는 Dockerfile 경로 반환
    private String getDockerfilePath(String language) {
        switch (language.toLowerCase()) {
            case "python":
                return "Dockerfile-python";
            case "java":
                return "Dockerfile-java";
            case "cpp":
                return "Dockerfile-cpp";
            default:
                return null;
        }
    }

    public String runDockerContainer(String imageName, String... command) {
        try {
            CreateContainerResponse container = dockerClient.createContainerCmd(imageName)
                    .withCmd(command)
                    .exec();

            dockerClient.startContainerCmd(container.getId()).exec();

            return "Container started with ID: " + container.getId();
        } catch (Exception e) {
            return "Error starting container: " + e.getMessage();
        }
    }

}
