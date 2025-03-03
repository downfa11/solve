package com.ns.solve.repository.problem;

import com.ns.solve.domain.problem.Problem;
import com.ns.solve.domain.problem.QProblem;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

@Repository
public class ProblemCustomRepositoryImpl implements ProblemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QProblem qProblem;

    public ProblemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qProblem = QProblem.problem;
    }

    // status가 '검수 전'인 Problem의 전체 목록을 조회하는 메서드 (Pagenation)
    @Override
    public Page<Problem> findProblemsByStatusPending(PageRequest pageRequest) {
        List<Problem> results = jpaQueryFactory
                .selectFrom(qProblem)
                .where(qProblem.isChecked.isFalse())  // check가 false인 문제: 검수 전
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        long total = jpaQueryFactory
                .selectFrom(qProblem)
                .where(qProblem.isChecked.isFalse())  // check가 false인 문제: 검수 전
                .fetchCount();  // 전체 문제 수를 구하는 쿼리

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

    private BooleanExpression typeEq(String type) {
        return type == null ? null : qProblem.type.eq(type);
    }
}
