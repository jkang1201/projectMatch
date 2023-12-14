package com.example.demo.dto.user.response;

import com.example.demo.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserReadProjectCrewResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String profileImgSrc;

    public static UserReadProjectCrewResponseDto of(User user) {
        return UserReadProjectCrewResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImgSrc(user.getProfileImgSrc())
                .build();
    }
}
