package com.ns.solve.repository;

import com.ns.solve.domain.Case;
import com.ns.solve.domain.Problem;
import com.ns.solve.domain.QProblem;
import com.ns.solve.domain.dto.ProblemFilter;
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
