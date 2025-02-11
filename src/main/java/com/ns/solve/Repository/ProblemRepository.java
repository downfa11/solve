package com.ns.solve.repository;

import com.ns.solve.domain.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemCustomRepository {

}
