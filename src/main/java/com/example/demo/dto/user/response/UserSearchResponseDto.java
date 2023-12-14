package com.example.demo.dto.user.response;

import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.model.user.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchResponseDto {
    private String email;
    private String nickname;
    private String profileImgSrc;
    private TrustGradeResponseDto trustGrade;

    public static UserSearchResponseDto of(User user, TrustGradeResponseDto trustGradeResponseDto) {

        return UserSearchResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .profileImgSrc(user.getProfileImgSrc())
                .trustGrade(trustGradeResponseDto)
                .build();
    }
}
