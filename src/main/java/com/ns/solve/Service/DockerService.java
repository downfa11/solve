package com.ns.solve.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.PullResponseItem;
import java.io.File;
import java.nio.file.Path;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DockerService {
    @Value("${ecr.image.uri:473749683913.dkr.ecr.ap-northeast-2.amazonaws.com/downfa11/solve}")
    private String ecrURI;

    @Value("${aws.ecr.region:ap-northeast-2}")
    private String region;

    private final DockerClient dockerClient;
    private final GitService gitService;

    public String buildDockerImage(String repoName) {
        try {
            Path dockerfilePath = Path.of(gitService.findDirectoryByRepoName(repoName) + "/Dockerfile");

            System.out.println(dockerfilePath);
            dockerClient.buildImageCmd(dockerfilePath.toFile())
                    .withTag(repoName)
                    .exec(new BuildImageResultCallback())
                    .awaitCompletion();

            List<Image> imageList = dockerClient.listImagesCmd().exec();
            System.out.println("imageList : " + imageList);
            return "Docker image " + repoName + " built successfully!";
        } catch (Exception e) {
            return "Error building Docker image: " + e.getMessage();
        }
    }

    //==============================================================================//
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