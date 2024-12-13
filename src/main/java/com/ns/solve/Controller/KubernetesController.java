package com.ns.solve.Controller;

import com.ns.solve.Service.ImageService;
import com.ns.solve.Service.KubernetesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/k8s")
@RequiredArgsConstructor
public class KubernetesController {
    private final ImageService imageService;
    private final KubernetesService kubernetesService;

    @PostMapping("/create-pod")
    public String createPod(@RequestParam String podName, @RequestParam String image) {
        return kubernetesService.createPod(podName, image);
    }

    @PostMapping("/execute")
    public String executeCommand(@RequestParam String podName, @RequestBody String command) {
        return kubernetesService.executeCommand(podName, command.split(" "));
    }

    @PostMapping("/ce")
    public String executePod(@RequestParam String podName, @RequestParam String image, @RequestParam String command) {
        return kubernetesService.createAndExecutePod(podName, image, command.split("\\s+"));
    }

    @PostMapping("/run-code")
    public String runCode(@RequestParam String language, @RequestParam String codePath, @RequestParam String podName) {
        String imageName = "code-runner:" + language;
        String buildResult = imageService.buildCodeImage(language, codePath, imageName);

        if (!buildResult.contains("Docker image built"))
            return buildResult;

        return kubernetesService.createPod(podName, imageName);
    }

    @PostMapping("/build-and-run")
    public String buildAndRunDockerImage(@RequestParam String language, @RequestParam String projectDir, @RequestParam String imageName, @RequestParam String command) {
        String buildResult = imageService.buildDockerImage(language, projectDir, imageName);
        if (buildResult.startsWith("Error")) {
            return buildResult;
        }

        return imageService.runDockerContainer(imageName, command.split(" "));
    }

}
