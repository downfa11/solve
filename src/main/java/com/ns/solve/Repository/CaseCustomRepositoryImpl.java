package com.ns.solve.Repository;

import com.ns.solve.Domain.Case;
import com.ns.solve.Domain.QCase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class CaseCustomRepositoryImpl implements CaseCustomRepository{
    private final JPAQueryFactory jpaQueryFactory;
    private final QCase qCase;

    public CaseCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qCase = QCase.case$;
    }

    @Override
    public Optional<Case> findByCaseId(Long caseId) {
        return Optional.empty();
    }
}
