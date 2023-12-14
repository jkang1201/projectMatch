package com.example.demo.dto.trust_grade.response;

import com.example.demo.model.trust_grade.TrustGrade;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrustGradeResponseDto {
    private String name;
    private int minimumScore;
    private int maximumScore;

    public static TrustGradeResponseDto of(TrustGrade trustGrade) {
        return TrustGradeResponseDto.builder()
                .name(trustGrade.getName())
                .minimumScore(trustGrade.getMinimumScore())
                .maximumScore(trustGrade.getMaximumScore())
                .build();
    }
}
