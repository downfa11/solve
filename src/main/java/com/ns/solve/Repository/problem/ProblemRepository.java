package com.ns.solve.repository.problem;

import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.problem.WargameProblem;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, ProblemCustomRepository {
    List<WargameProblem> findByType(String type);
}
