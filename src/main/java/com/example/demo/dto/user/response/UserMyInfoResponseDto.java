package com.example.demo.dto.user.response;

import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeInfoResponseDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserMyInfoResponseDto {

    private Long userId;

    private String email;

    private String nickname;

    private String profileImgSrc;

    private int trustScore;

    private TrustGradeInfoResponseDto trustGrade;

    private PositionInfoResponseDto position;

    private List<TechnologyStackInfoResponseDto> techStacks;

    private long projectHistoryTotalCount;

    private List<UserProjectHistoryInfoResponseDto> projectHistoryList;

    private String createDate;

    private String updateDate;

    public static UserMyInfoResponseDto of(
            Long userId,
            String email,
            String nickname,
            String profileImgSrc,
            int trustScore,
            TrustGradeInfoResponseDto trustGrade,
            PositionInfoResponseDto position,
            List<TechnologyStackInfoResponseDto> techStacks,
            long projectHistoryTotalCount,
            List<UserProjectHistoryInfoResponseDto> projectHistoryList,
            String createDate,
            String updateDate) {
        return UserMyInfoResponseDto.builder()
                .userId(userId)
                .email(email)
                .nickname(nickname)
                .profileImgSrc(profileImgSrc)
                .trustScore(trustScore)
                .trustGrade(trustGrade)
                .position(position)
                .techStacks(techStacks)
                .projectHistoryTotalCount(projectHistoryTotalCount)
                .projectHistoryList(projectHistoryList)
                .createDate(createDate)
                .updateDate(updateDate)
                .build();
    }
}
