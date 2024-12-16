package com.ns.solve.Controller;

import com.ns.solve.Service.GitService;
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
    private final GitService imageService;
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

}
