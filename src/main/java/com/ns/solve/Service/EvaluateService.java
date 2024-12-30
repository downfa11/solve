package com.ns.solve.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluateService {
    private final AssignmentService assignmentService;
    private final ProblemService problemService;
    private final GitService gitService;
    private final CaseService caseService;

    private final KubernetesService kubernetesService;
    private final DockerService dockerService;

    /*
    * 1. Assignment에 Github 주소를 제출한다.
    * 2. 해당 Problem의 Case 들을 불러온다.
    * 3. k8s 환경에서 빌드하고 Case들을 입력한다.
    * 4. 입력에 대한 결과들을 저장하고 정답과 비교, 평가한다. (케이스별 가중치)
    */
}
