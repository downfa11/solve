package com.ns.solve.repository;

import com.ns.solve.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProblemId(Long problemId);
    List<Comment> findByBoardId(Long boardId);

    List<Comment> findByType(String type);
}
