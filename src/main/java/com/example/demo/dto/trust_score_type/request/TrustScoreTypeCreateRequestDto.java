package com.example.demo.dto.trust_score_type.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrustScoreTypeCreateRequestDto {

    private Long upTrustScoreTypeId;

    private String trustScoreTypeName;

    private String trustGradeName;

    private Integer score;

    private String gubunCode;

    private String deleteStatus;
}
