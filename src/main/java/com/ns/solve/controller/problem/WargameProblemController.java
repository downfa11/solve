package com.ns.solve.controller.problem;

import com.ns.solve.domain.dto.WargameProblemDto;
import com.ns.solve.domain.problem.WargameProblem;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.problem.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wargame-problems")
public class WargameProblemController {
    private final ProblemService problemService;

    @Operation(summary = "문제 ID로 조회", description = "주어진 문제 ID로 문제를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "문제가 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제를 찾을 수 없습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MessageEntity> getWargameProblemById(@PathVariable Long id) {
        WargameProblemDto wargameProblemDto = problemService.getWargameProblemById(id);
        return ResponseEntity.ok(new MessageEntity("Wargame Problem found", wargameProblemDto));
    }

    @Operation(summary = "검수 완료된 문제 조회", description = "검수 완료된 문제들을 주어진 유형에 따라 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "완료된 문제들이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "완료된 문제들을 찾을 수 없습니다.")
    })
    @GetMapping
    public ResponseEntity<MessageEntity> getAllWargameProblems() {
        List<WargameProblem> wargameProblems = problemService.getAllWargameProblems();
        return ResponseEntity.ok(new MessageEntity("Wargame Problems fetched successfully", wargameProblems));
    }
}
