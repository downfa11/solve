package com.ns.solve.Repository;

import com.ns.solve.Domain.Case;
import com.ns.solve.Domain.Problem;
import com.ns.solve.Domain.ProblemFilter;
import com.ns.solve.Domain.QProblem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public class ProblemCustomRepositoryImpl implements ProblemCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;
    private final QProblem qProblem;

    public ProblemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory, QProblem qProblem){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qProblem = qProblem;
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
