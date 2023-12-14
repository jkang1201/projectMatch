package com.example.demo.dto.user.response;

import com.example.demo.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserBoardDetailResponseDto {
    private Long userId;
    private String nickName;
    private String userProfileImgSrc;

    public static UserBoardDetailResponseDto of(User user) {
        return UserBoardDetailResponseDto.builder()
                .userId(user.getId())
                .nickName(user.getNickname())
                .userProfileImgSrc(user.getProfileImgSrc())
                .build();
    }
}
