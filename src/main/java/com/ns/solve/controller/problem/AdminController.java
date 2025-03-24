package com.ns.solve.controller.problem;

import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.domain.dto.WargameProblemDto;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.service.problem.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary="검수를 위해 대기중인 문제 목록 조회", description = "제출된 문제를 검수하기 위해 대기중인 리스트를 조회합니다.")
    @GetMapping("/pending")
    public ResponseEntity<Page<Problem>> getPendingProblems(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        Page<Problem> problems = problemService.getPendingProblems(PageRequest.of(page, size));
        return new ResponseEntity<>(problems, HttpStatus.OK);
    }

    @Operation(summary = "검수 완료", description = "해당 문제의 검수를 완료해서 사용자들에게 보여집니다.")
    @PutMapping("/check/{id}")
    public ResponseEntity<MessageEntity> checkProblem(@PathVariable Long id) {
        WargameProblemDto checkedProblem = problemService.toggleProblemCheckStatus(id);
        return ResponseEntity.ok(new MessageEntity("Problem marked as checked", checkedProblem));
    }
}
