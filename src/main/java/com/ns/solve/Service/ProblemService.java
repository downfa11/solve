package com.ns.solve.Service;

import com.ns.solve.Domain.Membership;
import com.ns.solve.Domain.Problem;
import com.ns.solve.Domain.dto.ProblemDto;
import com.ns.solve.Domain.dto.ProblemFilter;
import com.ns.solve.Repository.MembershipRepository;
import com.ns.solve.Repository.ProblemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final String NOT_FOUND_ID_ERROR_MESSAGE = "Not Found this Index.";

    private final ProblemRepository problemRepository;
    private final MembershipRepository membershipRepository;

    public ProblemDto registerProblem(boolean isPublic, ProblemDto problemDto) {
        Membership membership = membershipRepository.findByMembershipId(problemDto.getMembershipId()).get();

        Problem problem = Problem.builder()
                .membership(membership)
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

        Membership membership = membershipRepository.findByMembershipId(problemDto.getMembershipId())
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
