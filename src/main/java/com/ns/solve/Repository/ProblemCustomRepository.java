package com.ns.solve.Repository;

import com.ns.solve.Domain.Case;
import com.ns.solve.Domain.Problem;
import com.ns.solve.Domain.ProblemFilter;
import java.util.List;

public interface ProblemCustomRepository {
    List<Problem> findByProblemId(Long problemId, ProblemFilter filter);
    List<Case> findCaseByProblemId(Long problemId);
}
