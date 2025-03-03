package com.ns.solve.controller.problem;

import com.ns.solve.domain.dto.ProblemSummary;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/problems")
public class ProblemController {
    private final ProblemService problemService;

    @PostMapping
    public ResponseEntity<MessageEntity> createProblem(@RequestBody Problem problem) {
        Problem createdProblem = problemService.createProblem(problem);
        return ResponseEntity.status(201).body(new MessageEntity("Problem created successfully", createdProblem));
    }

    @GetMapping
    public ResponseEntity<MessageEntity> getAllProblems() {
        List<Problem> problems = problemService.getAllProblems();
        return ResponseEntity.ok(new MessageEntity("Problems fetched successfully", problems));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getProblemById(@PathVariable Long id) {
        Optional<Problem> problem = problemService.getProblemById(id);
        if (problem.isPresent()) {
            return ResponseEntity.ok(new MessageEntity("Problem found", problem.get()));
        } else {
            return ResponseEntity.status(404).body(new MessageEntity("Problem not found", null));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageEntity> updateProblem(@PathVariable Long id, @RequestBody Problem problemDetails) {
        Problem updatedProblem = problemService.updateProblem(id, problemDetails);
        return ResponseEntity.ok(new MessageEntity("Problem updated successfully", updatedProblem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageEntity> deleteProblem(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/completed")
    public ResponseEntity<List<ProblemSummary>> getCompletedProblemsByType(@RequestParam String type, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        List<ProblemSummary> problemSummaries = problemService.getCompletedProblemsSummary(type, PageRequest.of(page, size));
        return new ResponseEntity<>(problemSummaries, HttpStatus.OK);
    }
}