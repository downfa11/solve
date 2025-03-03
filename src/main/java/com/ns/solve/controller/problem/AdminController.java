package com.ns.solve.controller.problem;

import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.service.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {
    private final ProblemService problemService;

    @GetMapping("/pending")
    public ResponseEntity<Page<Problem>> getPendingProblems(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Problem> problems = problemService.getPendingProblems(PageRequest.of(page, size));
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<MessageEntity> checkProblem(@PathVariable Long id) {
        Problem checkedProblem = problemService.markProblemAsChecked(id);
        return ResponseEntity.ok(new MessageEntity("Problem marked as checked", checkedProblem));
    }

    @PutMapping("/{id}/entire-count")
    public ResponseEntity<Problem> incrementEntireCount(@PathVariable Long id) {
        Problem updatedProblem = problemService.incrementEntireCount(id);
        return new ResponseEntity<>(updatedProblem, HttpStatus.OK);
    }

    @PutMapping("/{id}/correct-count")
    public ResponseEntity<Problem> incrementCorrectCount(@PathVariable Long id) {
        Problem updatedProblem = problemService.incrementCorrectCount(id);
        return new ResponseEntity<>(updatedProblem, HttpStatus.OK);
    }
}
