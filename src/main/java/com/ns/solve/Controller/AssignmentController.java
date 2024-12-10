package com.ns.solve.Controller;

import com.ns.solve.Domain.AssignmentDto;
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
    public void registerAssignment(@RequestBody AssignmentDto assignmentDto){
        assignmentService.registerAssignment(assignmentDto);
    }

    @PutMapping("/update/{AssignmentId}")
    public void updateAssignment(@PathVariable Long assignmentId){
        assignmentService.updateAssignment(assignmentId);
    }

    @DeleteMapping("/delete/{AssignmentId}")
    public void deleteAssignment(@PathVariable Long assignmentId){
        assignmentService.deleteAssignment(assignmentId);
    }

    @GetMapping("/{AssignmentId}")
    public void findAssignment(@PathVariable Long assignmentId){
        assignmentService.findAssignment(assignmentId);
    }
}

