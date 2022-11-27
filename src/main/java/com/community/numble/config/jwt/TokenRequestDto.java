package com.community.numble.config.jwt;

import lombok.*;

@Getter
@NoArgsConstructor
public class TokenRequestDto {
    private String accessToken;
    private String refreshToken;
}
