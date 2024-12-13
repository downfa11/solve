package com.ns.solve.Service;

import com.ns.solve.Domain.Membership;
import com.ns.solve.Repository.MembershipRepository;
import com.ns.solve.Utils.JwtToken;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;

    public JwtToken LoginMembership(Long membershipId){
        return null;
    }

    private Optional<Membership> findByMembershipId(Long membershipId){
        return membershipRepository.findByMembershipId(membershipId);
    }
}
