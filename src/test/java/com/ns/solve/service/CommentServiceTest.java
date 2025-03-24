package com.ns.solve.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.ns.solve.domain.Comment;
import com.ns.solve.repository.CommentRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment();
        testComment.setId(1L);
        testComment.setContent("Test Comment");
        testComment.setType("free");
    }

    @Test
    void testCreateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        Comment createdComment = commentService.createComment(testComment);

        assertNotNull(createdComment);
        assertEquals("Test Comment", createdComment.getContent());
        verify(commentRepository, times(1)).save(testComment);
    }

    @Test
    void testGetAllComments() {
        when(commentRepository.findAll()).thenReturn(List.of(testComment));

        List<Comment> comments = commentService.getAllComments();

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
        assertEquals("Test Comment", comments.get(0).getContent());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void testGetCommentsByProblemId() {
        when(commentRepository.findByProblemId(1L)).thenReturn(List.of(testComment));

        List<Comment> comments = commentService.getCommentsByProblemId(1L);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
        verify(commentRepository, times(1)).findByProblemId(1L);
    }

    @Test
    void testGetCommentsByBoardId() {
        when(commentRepository.findByBoardId(1L)).thenReturn(List.of(testComment));

        List<Comment> comments = commentService.getCommentsByBoardId(1L);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
        verify(commentRepository, times(1)).findByBoardId(1L);
    }

    @Test
    void testGetCommentById() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));

        Optional<Comment> comment = commentService.getCommentById(1L);

        assertTrue(comment.isPresent());
        assertEquals("Test Comment", comment.get().getContent());
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateComment() {
        Comment updatedDetails = new Comment();
        updatedDetails.setContent("Updated Comment");

        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(updatedDetails);

        Comment updatedComment = commentService.updateComment(1L, updatedDetails);

        assertNotNull(updatedComment);
        assertEquals("Updated Comment", updatedComment.getContent());
        verify(commentRepository, times(1)).findById(1L);
        verify(commentRepository, times(1)).save(testComment);
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).deleteById(1L);
    }
}
