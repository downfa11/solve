package com.ns.solve.service;

import com.ns.solve.domain.Problem;
import com.ns.solve.domain.dto.ProblemDto;
import com.ns.solve.domain.dto.ProblemFilter;
import com.ns.solve.repository.ProblemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final String NOT_FOUND_ID_ERROR_MESSAGE = "Not Found this Index.";

    private final ProblemRepository problemRepository;

    public ProblemDto registerProblem(boolean isPublic, ProblemDto problemDto) {
        Problem problem = Problem.builder()
                .isPublic(isPublic)
                .status(problemDto.getStatus())
                .deadline(problemDto.getDeadline())
                .detail(problemDto.getDetail())
                .caseList(problemDto.getCaseList())
                .build();

        return convertProblemDto(problemRepository.save(problem));
    }

    public ProblemDto monitorProblem(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        return convertProblemDto(problem);
    }

    public void updateProblem(Long problemId, boolean isPublic, ProblemDto problemDto) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        problem.setIsPublic(isPublic);
        problem.setStatus(problemDto.getStatus());
        problem.setDeadline(problemDto.getDeadline());
        problem.setDetail(problemDto.getDetail());

        problemRepository.save(problem);
    }

    public void deleteProblem(Long problemId) {
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        problemRepository.delete(problem);
    }

    public ProblemDto findProblem(Long problemId) {
        Problem problem =  problemRepository.findById(problemId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        return convertProblemDto(problem);
    }

    private List<Problem> findByProblemId(Long problemId){
        ProblemFilter filter = ProblemFilter.builder()
                .problemStatus(null)
                .regions(null)
                .build();

        return problemRepository.findByProblemId(problemId, filter);
    }

    private ProblemDto convertProblemDto(Problem problem){
        return ProblemDto.builder()
                .membershipId(problem.getProblemId())
                .title(problem.getTitle())
                .detail(problem.getDetail())
                .status(problem.getStatus())
                .deadline(problem.getDeadline())
                .caseList(problem.getCaseList())
                .build();
    }
}
