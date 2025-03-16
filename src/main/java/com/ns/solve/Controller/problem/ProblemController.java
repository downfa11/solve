package com.ns.solve.controller.problem;

import com.ns.solve.domain.dto.ProblemSummary;
import com.ns.solve.domain.dto.UserDto;
import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.problem.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

    @Operation(summary = "문제 생성", description = "새로운 문제를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "문제가 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @PostMapping
    public ResponseEntity<MessageEntity> createProblem(@RequestBody Problem problem) {
        Problem createdProblem = problemService.createProblem(problem);
        return ResponseEntity.status(201).body(new MessageEntity("Problem created successfully", createdProblem));
    }

    @Operation(summary = "모든 문제 조회", description = "모든 문제를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제들이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")
    })
    @GetMapping
    public ResponseEntity<MessageEntity> getAllProblems() {
        List<Problem> problems = problemService.getAllProblems();
        return ResponseEntity.ok(new MessageEntity("Problems fetched successfully", problems));
    }

    @Operation(summary = "문제 ID로 조회", description = "주어진 문제 ID로 문제를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제가 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getProblemById(@PathVariable Long id) {
        Optional<Problem> problem = problemService.getProblemById(id);
        if (problem.isPresent()) {
            return ResponseEntity.ok(new MessageEntity("Problem found", problem.get()));
        } else {
            return ResponseEntity.status(404).body(new MessageEntity("Problem not found", null));
        }
    }

    @Operation(summary = "문제 수정", description = "주어진 문제 ID로 문제를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제가 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageEntity> updateProblem(@PathVariable Long id, @RequestBody Problem problemDetails) {
        Problem updatedProblem = problemService.updateProblem(id, problemDetails);
        return ResponseEntity.ok(new MessageEntity("Problem updated successfully", updatedProblem));
    }

    @Operation(summary = "문제 삭제", description = "주어진 문제 ID로 문제를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제가 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageEntity> deleteProblem(@PathVariable Long id) {
        problemService.deleteProblem(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "검수 완료된 문제 조회", description = "검수 완료된 문제들을 주어진 유형에 따라 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "완료된 문제들이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "완료된 문제들을 찾을 수 없습니다.")
    })
    @GetMapping("/completed")
    public ResponseEntity<List<ProblemSummary>> getCompletedProblemsByType(
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<ProblemSummary> problemSummaries = problemService.getCompletedProblemsSummary(type, PageRequest.of(page, size));
        return new ResponseEntity<>(problemSummaries, HttpStatus.OK);
    }

    @Operation(summary = "문제 풀이", description = "주어진 문제에 대해 풀이 결과를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제 풀이 결과가 성공적으로 반환되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}/solve")
    public ResponseEntity<MessageEntity> solveProblem(@PathVariable Long probelmId,@RequestParam Long userId, @RequestParam String flag){
        return ResponseEntity.ok(new MessageEntity("Problem Solve Result", problemService.solveProblem(userId, probelmId, flag)));
    }

    @Operation(summary = "첫 번째 문제 풀이 사용자 조회", description = "주어진 문제 ID에 대해 가장 먼저 문제를 푼 사용자를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "첫 번째 문제 풀이 사용자가 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없거나 풀이한 사용자가 없습니다.")
    })
    @GetMapping("/{id}/firstblood")
    public ResponseEntity<MessageEntity> firstBlood(@PathVariable Long problemId) {
        Optional<UserDto> firstSolver = problemService.firstBlood(problemId);
        if (firstSolver.isPresent()) {
            return ResponseEntity.ok(new MessageEntity("First solver found", firstSolver.get()));
        } else {
            return ResponseEntity.status(404).body(new MessageEntity("No solver found for this problem", null));
        }
    }
}
