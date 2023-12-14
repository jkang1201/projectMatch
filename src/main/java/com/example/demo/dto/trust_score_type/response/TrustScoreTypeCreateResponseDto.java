package com.example.demo.dto.trust_score_type.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class TrustScoreTypeCreateResponseDto {

    private Long id;

    private String upTrustScoreTypeName;

    private String trustGradeName;

    private Integer score;

    private String gubunCode;

    private String deleteStatus;

    private LocalDateTime createDate;
}
