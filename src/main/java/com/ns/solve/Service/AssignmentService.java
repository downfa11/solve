package com.ns.solve.Service;

import com.ns.solve.Domain.AssignmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssignmentService {
    private final ImageService imageService;

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
}
