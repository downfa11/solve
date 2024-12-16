package com.ns.solve.Controller;

import com.ns.solve.Service.DockerService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/docker")
public class DockerController {
    private final DockerService dockerService;

    @GetMapping("build")
    public ResponseEntity<String> buildDockerImage(@RequestParam String repoName){
        return ResponseEntity.ok(dockerService.buildDockerImage(repoName));
    }

}
