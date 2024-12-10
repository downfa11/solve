package com.ns.solve.Service;

import com.ns.solve.Domain.Membership;
import com.ns.solve.Repository.MembershipRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;


    private Optional<Membership> findByMembershipId(Long membershipId){
        return membershipRepository.findByMembershipId(membershipId);
    }
}
