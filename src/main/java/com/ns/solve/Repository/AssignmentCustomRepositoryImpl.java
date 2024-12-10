package com.ns.solve.Repository;

import com.ns.solve.Domain.Assignment;
import com.ns.solve.Domain.QAssignment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;

public class AssignmentCustomRepositoryImpl implements AssignmentCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QAssignment qAssignment;

    public AssignmentCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory, QAssignment qAssignment){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qAssignment = qAssignment;
    }


    @Override
    public Optional<Assignment> findByAssignmentId(Long assignmentId) {
        return Optional.empty();
    }
}
