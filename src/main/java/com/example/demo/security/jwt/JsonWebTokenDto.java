package com.example.demo.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class JsonWebTokenDto {

    private String accessToken;
    private String refreshToken;

    public static JsonWebTokenDto of(String accessToken, String refreshToken) {
        return JsonWebTokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
