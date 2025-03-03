package com.ns.solve.controller;

import com.ns.solve.domain.Comment;
import com.ns.solve.domain.dto.MessageEntity;
import com.ns.solve.service.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<MessageEntity> createComment(@RequestBody Comment comment) {
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(new MessageEntity("Comment created successfully", createdComment));
    }

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<MessageEntity> getCommentsByProblemId(@PathVariable Long problemId) {
        List<Comment> comments = commentService.getCommentsByProblemId(problemId);
        return ResponseEntity.ok(new MessageEntity("Comments retrieved successfully", comments));
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<MessageEntity> getCommentsByBoardId(@PathVariable Long boardId) {
        List<Comment> comments = commentService.getCommentsByBoardId(boardId);
        return ResponseEntity.ok(new MessageEntity("Comments retrieved successfully", comments));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageEntity> updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
        Comment updatedComment = commentService.updateComment(id, commentDetails);
        return ResponseEntity.ok(new MessageEntity("Comment updated successfully", updatedComment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageEntity> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok(new MessageEntity("Comment deleted successfully", null));
    }
}
