package com.example.demo.dto.trust_score.response;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrustScoreUpdateResponseDto {
    @NotNull(message = "사용자값은 필수입니다.")
    private Long userId;

    private int totalScore;
    private int scoreChange;
}
