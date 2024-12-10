package com.ns.solve.Service;

import com.ns.solve.Domain.Case;
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

}
