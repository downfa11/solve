package com.ns.solve.Utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JwtToken {

    @Getter
    private final Long membershipId;
    @Getter
    private final String jwtToken;
    @Getter
    private final String refreshToken;


    public static JwtToken generateJwtToken(Long membershipId, String membershipJwtToken,
                                            String membershipRefreshToken) {

        return new JwtToken(
                membershipId, membershipJwtToken, membershipRefreshToken
        );
    }


}
