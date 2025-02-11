package com.ns.solve.repository;

import com.ns.solve.domain.Case;
import com.ns.solve.domain.Problem;
import com.ns.solve.domain.dto.ProblemFilter;
import java.util.List;

public interface ProblemCustomRepository {
    List<Problem> findByProblemId(Long problemId, ProblemFilter filter);
    List<Case> findCaseByProblemId(Long problemId);
}
