package com.ns.solve.Controller;

import com.ns.solve.Domain.dto.AssignmentDto;
import com.ns.solve.Service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/assignment")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/register")
    public AssignmentDto registerAssignment(@RequestBody AssignmentDto assignmentDto){
        return assignmentService.registerAssignment(assignmentDto);
    }

    @PutMapping("/update/{assignmentId}")
    public AssignmentDto updateAssignment(@PathVariable Long assignmentId, @RequestBody AssignmentDto assignmentDto){
        return assignmentService.updateAssignment(assignmentId, assignmentDto);
    }

    @DeleteMapping("/delete/{assignmentId}")
    public void deleteAssignment(@PathVariable Long assignmentId){

        assignmentService.deleteAssignment(assignmentId);
    }

    @GetMapping("/{assignmentId}")
    public AssignmentDto findAssignment(@PathVariable Long assignmentId){
        return assignmentService.findAssignment(assignmentId);
    }
}

