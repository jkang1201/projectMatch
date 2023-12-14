package com.example.demo.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserTokenInfoResponseDto {

    private String accessToken;

    private String refreshToken;

    public static UserTokenInfoResponseDto of(String accessToken, String refreshToken) {
        return UserTokenInfoResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
