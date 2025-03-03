package com.ns.solve.repository.problem;

import com.ns.solve.domain.problem.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProblemCustomRepository {
    Page<Problem> findProblemsByStatusPending(PageRequest pageRequest);
    Page<Problem> findProblemsByStatusAndType(String type, PageRequest pageRequest);
}
