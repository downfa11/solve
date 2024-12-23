package com.ns.solve.Repository;

import com.ns.solve.Domain.Case;
import com.ns.solve.Domain.Problem;
import com.ns.solve.Domain.QProblem;
import com.ns.solve.Domain.dto.ProblemFilter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemCustomRepositoryImpl implements ProblemCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final QProblem qProblem;

    public ProblemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qProblem = QProblem.problem;
    }

    @Override
    public List<Problem> findByProblemId(Long problemId, ProblemFilter filter) {
        return null;
    }

    @Override
    public List<Case> findCaseByProblemId(Long problemId) {
        return null;
    }
}
