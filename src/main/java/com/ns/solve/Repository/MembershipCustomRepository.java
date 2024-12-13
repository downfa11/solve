package com.ns.solve.Repository;

import com.ns.solve.Domain.Membership;
import java.util.Optional;

public interface MembershipCustomRepository {
    Optional<Membership> findByMembershipId(Long membershipId);
    Optional<Membership> findByAccount(String membershipAccount);
}
