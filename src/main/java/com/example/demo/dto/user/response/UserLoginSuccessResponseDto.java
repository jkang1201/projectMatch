package com.example.demo.dto.user.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserLoginSuccessResponseDto {

    private Long userId;
    private String email;
    private String nickname;

    public static UserLoginSuccessResponseDto of(Long userId, String email, String nickname) {
        return UserLoginSuccessResponseDto.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .build();
    }
}
