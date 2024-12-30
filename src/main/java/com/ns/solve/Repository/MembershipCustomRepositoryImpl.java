package com.ns.solve.Repository;

import com.ns.solve.Domain.Membership;
import com.ns.solve.Domain.QMembership;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
public class MembershipCustomRepositoryImpl implements MembershipCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;
    private final QMembership qMembership;

    public MembershipCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory){
        this.jpaQueryFactory=jpaQueryFactory;
        this.qMembership = QMembership.membership;
    }

    @Override
    public Optional<Membership> findByMembershipId(Long membershipId) {
        Membership result = jpaQueryFactory
                .selectFrom(qMembership)
                .where(qMembership.membershipId.eq(membershipId))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Membership> findByAccount(String membershipAccount) {
        Membership result = jpaQueryFactory
                .selectFrom(qMembership)
                .where(qMembership.account.eq(membershipAccount))
                .fetchOne();
        return Optional.ofNullable(result);
    }
}
