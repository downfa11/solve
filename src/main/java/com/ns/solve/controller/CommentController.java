package com.ns.solve.controller;

import com.ns.solve.domain.Comment;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "댓글 생성", description = "새로운 댓글을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 생성되었습니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 요청입니다.")
    })
    @PostMapping
    public ResponseEntity<MessageEntity> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(new MessageEntity("Comment created successfully", createdComment));
    }

    @Operation(summary = "문제에 대한 댓글 조회", description = "주어진 문제 ID에 대한 모든 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "문제에 대한 댓글을 찾을 수 없습니다.")
    })
    @GetMapping("/problem/{problemId}")
    public ResponseEntity<MessageEntity> getCommentsByProblemId(@PathVariable Long problemId) {
        List<Comment> comments = commentService.getCommentsByProblemId(problemId);
        return ResponseEntity.ok(new MessageEntity("Comments retrieved successfully", comments));
    }

    @Operation(summary = "게시판에 대한 댓글 조회", description = "주어진 게시판 ID에 대한 모든 댓글을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 조회되었습니다."),
            @ApiResponse(responseCode = "404", description = "게시판에 대한 댓글을 찾을 수 없습니다.")
    })
    @GetMapping("/board/{boardId}")
    public ResponseEntity<MessageEntity> getCommentsByBoardId(@PathVariable Long boardId) {
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(new MessageEntity("Comments retrieved successfully", comments));
    }

    @Operation(summary = "댓글 수정", description = "주어진 댓글 ID를 기준으로 댓글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 수정되었습니다."),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MessageEntity> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Comment updatedComment = commentService.updateComment(id, commentDetails);
        return ResponseEntity.ok(new MessageEntity("Comment updated successfully", updatedComment));
    }

    @Operation(summary = "댓글 삭제", description = "주어진 댓글 ID를 기준으로 댓글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "댓글이 성공적으로 삭제되었습니다."),
            @ApiResponse(responseCode = "404", description = "댓글을 찾을 수 없습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageEntity> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageEntity("Comment deleted successfully", null));
    }
}
