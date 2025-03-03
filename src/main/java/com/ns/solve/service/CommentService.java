package com.ns.solve.service;

import com.ns.solve.domain.Comment;
import com.ns.solve.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment createComment(Comment comment) { return commentRepository.save(comment); }
    public List<Comment> getAllComments() { return commentRepository.findAll(); }
    public List<Comment> getCommentsByProblemId(Long problemId) { return commentRepository.findByProblemId(problemId);}
    public List<Comment> getCommentsByBoardId(Long boardId) {
        return commentRepository.findByBoardId(boardId);
    }
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }
    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        if ("problem".equals(comment.getType())) {
            comment.setContent(commentDetails.getContent());
        } else if ("board".equals(comment.getType())) {
            comment.setContent(commentDetails.getContent());
        }

        return commentRepository.save(comment);
    }
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}