package com.ns.solve.Service;

import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Container;
import io.kubernetes.client.openapi.models.V1ObjectMeta;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodSpec;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KubernetesService {

    private final CoreV1Api api;

    public String createPod(String podName, String image) {
        V1Pod pod = new V1Pod()
                .metadata(new V1ObjectMeta().name(podName))
                .spec(new V1PodSpec()
                        .containers(Collections.singletonList(
                                new V1Container()
                                        .name("test-container")
                                        .image(image)
                                        .command(Collections.singletonList("tail"))
                                        .args(Arrays.asList("-f", "/dev/null"))
                        ))
                );


        try {
            V1Pod createdPod = api.createNamespacedPod("default", pod, null, null, null, null);
            return "Pod created: " + createdPod.getMetadata().getName();
        } catch (ApiException e) {
            System.out.println("Error creating Pod in Service: " + e.getResponseBody());
            System.out.println("HTTP Status Code: " + e.getCode());
            System.out.println("Response Headers: " + e.getResponseHeaders());
            return "Pod failed: " + e;
        }
    }

    public String executeCommand(String podName, String... command) {
        List<String> commandList = new ArrayList<>();
        commandList.add("kubectl");
        commandList.add("exec");
        commandList.add(podName);
        commandList.add("--");
        commandList.addAll(Arrays.asList(command));

        ProcessBuilder processBuilder = new ProcessBuilder(commandList);

        try {
            Process process = processBuilder.start();
            try (Scanner scanner = new Scanner(process.getInputStream())) {
                String output = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                System.out.println("executeCommand: " + output);
                return output;
            }
        } catch (IOException e) {
            System.out.println("Error executing command on pod: " + podName + " " + e.getMessage() +" "+ e);
            return "Error executing command: " + e.getMessage();
        }
    }

    public String createAndExecutePod(String podName, String image, String... command) {
        String createResult = createPod(podName, image);
        System.out.println("createAndExecutePod: " + createResult);

        String result = null;

        try {
            if (waitPodToReady(podName, 60))
                result = executeCommand(podName, command);
            else System.out.println("Pod is not in a running state after waiting.");
        } catch (Exception e){
            System.out.println("createAndExecutePod error: "+ e);
        }

        return result;
    }

    public boolean waitPodToReady(String podName, int timeout) throws ApiException, InterruptedException {
        int curDelay = 0;

        while (curDelay < timeout) {
            V1Pod pod = api.readNamespacedPod(podName, "default", null);

            String status = pod.getStatus().getPhase();
            if ("Running".equals(status)) return true;

            else System.out.println("Pod Status: "+ status);

            Thread.sleep(1000);
            curDelay++;
        }
        return false;
    }

}
