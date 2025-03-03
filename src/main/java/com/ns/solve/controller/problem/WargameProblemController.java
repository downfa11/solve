package com.ns.solve.controller.problem;

import com.ns.solve.domain.problem.WargameProblem;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.problem.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wargame-problems")
public class WargameProblemController {
    private final ProblemService problemService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getWargameProblemById(@PathVariable Long id) {
        WargameProblem wargameProblem = problemService.getWargameProblemById(id);
        return ResponseEntity.ok(new MessageEntity("Wargame Problem found", wargameProblem));
    }

    @GetMapping
    public ResponseEntity<MessageEntity> getAllWargameProblems() {
        List<WargameProblem> wargameProblems = problemService.getWargameProblems();
        return ResponseEntity.ok(new MessageEntity("Wargame Problems fetched successfully", wargameProblems));
    }
}
