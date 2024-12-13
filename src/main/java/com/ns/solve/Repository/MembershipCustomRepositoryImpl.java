package com.ns.solve.Repository;

import com.ns.solve.Domain.Membership;
import com.ns.solve.Domain.QMembership;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;

public class MembershipCustomRepositoryImpl implements MembershipCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMembership qMembership;


    public MembershipCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory, QMembership qMembership){
        this.jpaQueryFactory = jpaQueryFactory;
        this.qMembership = qMembership;
    }


    @Override
    public Optional<Membership> findByMembershipId(Long membershipId) {
        return Optional.empty();
    }

    @Override
    public Optional<Membership> findByAccount(String membershipAccount) {
        return null;
    }
}
