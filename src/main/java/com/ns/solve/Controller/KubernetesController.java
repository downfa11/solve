package com.ns.solve.controller;

import com.ns.solve.service.GitService;
import com.ns.solve.service.KubernetesService;
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
    private final KubernetesService kubernetesService;

    @PostMapping("/create-pod")
    public String createPod(@RequestParam String podName, @RequestParam String image) {
        return kubernetesService.createPod(podName, image);
    }

    @PostMapping("/execute")
    public String executeCommand(@RequestParam String podName, @RequestBody String command) {
        // executeCommand("python3", "-c", "print('Hello from Python!')");

        // executeCommand("g++", "-o", "/tmp/hello", "/tmp/hello.cpp");
        // executeCommand("/tmp/hello");

        // executeCommand("sh", "-c", "echo 'Hello, Kubernetes!'");

        // executeCommand("ping", "-c", "4", "google.com");
        return kubernetesService.executeCommand(podName, command.split(" "));
    }

    @PostMapping("/createAndExecute")
    public String executePod(@RequestParam String podName, @RequestParam String image, @RequestParam String command) {
        return kubernetesService.createAndExecutePod(podName, image, command.split("\\s+"));
    }

}
