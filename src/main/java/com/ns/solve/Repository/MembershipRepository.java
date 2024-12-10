package com.ns.solve.Repository;

import com.ns.solve.Domain.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long>, MembershipCustomRepository{
}
