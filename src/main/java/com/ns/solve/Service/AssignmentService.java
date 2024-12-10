package com.ns.solve.Service;

import com.ns.solve.Domain.Assignment;
import com.ns.solve.Domain.dto.AssignmentDto;
import com.ns.solve.Repository.AssignmentRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final ImageService imageService;
    private final AssignmentRepository assignmentRepository;

    public void registerAssignment(AssignmentDto assignmentDto) {
        System.out.println("Assignment 등록: " + assignmentDto);

    }

    public void updateAssignment(Long assignmentId) {
        System.out.println("Assignment 업데이트: ID = " + assignmentId);
    }

    public void deleteAssignment(Long assignmentId) {
        System.out.println("Assignment 삭제: ID = " + assignmentId);
    }

    public void findAssignment(Long assignmentId) {
        System.out.println("Assignment 검색: ID = " + assignmentId);
    }

    private Optional<Assignment> findByAssignmentId(Long assignmentId){
        return assignmentRepository.findByAssignmentId(assignmentId);
    }

}
