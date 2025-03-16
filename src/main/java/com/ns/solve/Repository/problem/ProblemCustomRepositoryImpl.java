package com.ns.solve.repository.problem;

import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.problem.QProblem;
import com.ns.solve.domain.problem.QWargameProblem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemCustomRepositoryImpl implements ProblemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QWargameProblem qWargameProblem;
    private final QProblem qProblem;

    public ProblemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qWargameProblem = QWargameProblem.wargameProblem;
        this.qProblem = QProblem.problem;
    }

    // status가 '검수 전'인 Problem의 전체 목록을 조회하는 메서드 (Pagenation)
    @Override
    public Page<Problem> findProblemsByStatusPending(PageRequest pageRequest) {
        List<Problem> results = jpaQueryFactory
                .selectFrom(qProblem)
                .where(qProblem.isChecked.isFalse())
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qProblem)
                .where(qProblem.isChecked.isFalse())
                .fetchCount();

        return new PageImpl<>(results, pageRequest, total);
    }

    // status가 '검수 완료'인 Problem을 type과 상속된 데이터에 맞게 리스트를 조회하는 메서드 (Pagenation)
    @Override
    public Page<Problem> findProblemsByStatusAndType(String type, PageRequest pageRequest) {
        List<Problem> results = jpaQueryFactory
                .selectFrom(qProblem)
                .where(qProblem.isChecked.isTrue().and(typeEq(type)))
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qProblem)
                .where(qProblem.isChecked.isTrue().and(typeEq(type)))
                .fetchCount();

        return new PageImpl<>(results, pageRequest, total);
    }

    @Override
    public Boolean matchFlagToProblems(Long problemId, String attemptedFlag ) {
        String correctFlag  = jpaQueryFactory.select(qWargameProblem.flag)
                .from(qWargameProblem)
                .where(qWargameProblem.id.eq(problemId))
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetchOne();

        return Objects.equals(attemptedFlag , correctFlag );
    }

    private BooleanExpression typeEq(String type) {
        return type == null ? null : qProblem.type.eq(type);
    }
}
