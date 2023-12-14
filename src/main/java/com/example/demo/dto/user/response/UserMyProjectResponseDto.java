package com.example.demo.dto.user.response;

import com.example.demo.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMyProjectResponseDto {
    private Long userId;
    private String profileImgSrc;

    public static UserMyProjectResponseDto of(User user) {
        return UserMyProjectResponseDto.builder()
                .userId(user.getId())
                .profileImgSrc(user.getProfileImgSrc())
                .build();
    }
}
