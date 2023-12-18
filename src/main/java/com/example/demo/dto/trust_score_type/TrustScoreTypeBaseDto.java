package com.example.demo.dto.trust_score_type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrustScoreTypeBaseDto {

    // 상위신뢰점수타입 식별자
    private Long upTrustScoreTypeId;

    // 신뢰점수타입명
    @NotBlank(message = "신뢰점수타입명은 비어있을 수 없습니다.")
    private String trustScoreTypeName;

    // 신뢰점수타입 등급명
    private String trustGradeName;

    // 신뢰점수타입 점수
    private Integer score;

    // 신뢰점수 부여 및 차감 코드
    @NotBlank(message = "구분 코드는 비어 있을 수 없습니다")
    @Pattern(regexp = "^[PM]$", message = "gubunCode는 'P' 또는 'M'이어야 합니다")
    private String gubunCode;

    // 신뢰점수타입 논리적 삭제 여부 (비활성화)
    @NotBlank
    @Pattern(regexp = "^[YN]$", message = "삭제여부는 'Y' 혹은 'N'이어야 합니다.")
    private String deleteStatus;
}
