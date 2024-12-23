package com.ns.solve.Service;

import com.ns.solve.Domain.Assignment;
import com.ns.solve.Domain.Membership;
import com.ns.solve.Domain.Problem;
import com.ns.solve.Domain.dto.AssignmentDto;
import com.ns.solve.Repository.AssignmentRepository;
import com.ns.solve.Repository.MembershipRepository;
import com.ns.solve.Repository.ProblemRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final String NOT_FOUND_ID_ERROR_MESSAGE = "Not Found this Index.";

    private final AssignmentRepository assignmentRepository;
    private final MembershipRepository membershipRepository;
    private final ProblemRepository problemRepository;

    public AssignmentDto registerAssignment(AssignmentDto assignmentDto) {
        Membership membership = membershipRepository.findByMembershipId(assignmentDto.getMembershipId())
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));
        Problem problem = problemRepository.findById(assignmentDto.getProblemId())
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        Assignment assignment = Assignment.builder()
                .membership(membership)
                .problem(problem)
                .detail(assignmentDto.getDetail())
                .comment(assignmentDto.getComment())
                .gitRepository(assignmentDto.getGitDirectory())
                .build();

        return convertAssignmentDto(assignment);
    }

    public AssignmentDto updateAssignment(Long assignmentId, AssignmentDto assignmentDto) {
        Assignment assignment = assignmentRepository.findByAssignmentId(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        assignment.setDetail(assignment.getDetail());
        assignment.setComment(assignment.getComment());
        assignment.setGitRepository(assignment.getGitRepository());

        return convertAssignmentDto(assignmentRepository.save(assignment));
    }

    public void deleteAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findByAssignmentId(assignmentId)
                        .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        assignmentRepository.delete(assignment);
    }

    public AssignmentDto findAssignment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findByAssignmentId(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException(NOT_FOUND_ID_ERROR_MESSAGE));

        return convertAssignmentDto(assignment);
    }

    private Optional<Assignment> findByAssignmentId(Long assignmentId){
        return assignmentRepository.findByAssignmentId(assignmentId);
    }

    private AssignmentDto convertAssignmentDto(Assignment assignment){
        return AssignmentDto.builder()
                .membershipId(assignment.getMembership().getMembershipId())
                .problemId(assignment.getProblem().getProblemId())
                .detail(assignment.getDetail())
                .comment(assignment.getComment())
                .gitDirectory(assignment.getGitRepository())
                .build();
    }

}
