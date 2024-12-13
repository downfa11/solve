package com.ns.solve.Service;

import com.ns.solve.Domain.Problem;
import com.ns.solve.Domain.dto.ProblemFilter;
import com.ns.solve.Domain.dto.ProblemDto;
import com.ns.solve.Repository.ProblemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;

    public void registerProblem(boolean isPublic, ProblemDto problemDto) {
        if (isPublic) {
            System.out.println("공개 문제 등록: " + problemDto);
        } else {
            System.out.println("비공개 문제 등록: " + problemDto);
        }
    }

    public void monitorProblem(Long problemId) {
        System.out.println("문제 모니터링: ID = " + problemId);
    }

    public void updateProblem(Long problemId) {
        System.out.println("문제 업데이트: ID = " + problemId);
    }

    public void deleteProblem(Long problemId) {
        System.out.println("문제 삭제: ID = " + problemId);
    }

    public void findProblem(Long problemId) {
        System.out.println("문제 조회: ID = " + problemId);
    }

    private List<Problem> findByProblemId(Long problemId){
        ProblemFilter filter = ProblemFilter.builder()
                .problemStatus(null)
                .regions(null)
                .build();
        return problemRepository.findByProblemId(problemId, filter);
    }
}
