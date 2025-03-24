package com.ns.solve.repository;

import com.ns.solve.domain.Solved;
import com.ns.solve.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SolvedRepository extends JpaRepository<Solved, Long> {
    // problemId를 통해서 가장 처음 해당 문제를 푼 사람을 확인하는 메서드
    @Query("SELECT s.solvedUser FROM Solved s WHERE s.solvedProblem.id = :problemId ORDER BY s.solvedTime ASC")
    Optional<User> findFirstSolverByProblemId(Long problemId);
}
