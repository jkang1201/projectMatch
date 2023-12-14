package com.example.demo.dto.trust_score_type.response;

import com.example.demo.model.trust_score.TrustScoreType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TrustScoreTypeReadResponseDto {

    private Long trustScoreTypeId;
    private String upTrustScoreTypeName;
    private String trustScoreTypeName;
    private String trustGradeName;
    private Integer score;
    private String gubunCode;
    private String deleteStatus;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static TrustScoreTypeReadResponseDto of(TrustScoreType trustScoreType) {
        return TrustScoreTypeReadResponseDto.builder()
                .trustScoreTypeId(trustScoreType.getId())
                .upTrustScoreTypeName(getUpTrustScoreTypeName(trustScoreType.getUpTrustScoreType()))
                .trustScoreTypeName(trustScoreType.getTrustScoreTypeName())
                .trustGradeName(trustScoreType.getTrustGradeName())
                .score(trustScoreType.getScore())
                .gubunCode(trustScoreType.getGubunCode())
                .deleteStatus(trustScoreType.getDeleteStatus())
                .createDate(trustScoreType.getCreateDate())
                .updateDate(trustScoreType.getUpdateDate())
                .build();
    }

    private static String getUpTrustScoreTypeName(TrustScoreType trustScoreType) {
        if (trustScoreType == null) {
            return null;
        }
        return trustScoreType.getTrustScoreTypeName();
    }

    @QueryProjection
    public TrustScoreTypeReadResponseDto(
            Long trustScoreTypeId,
            String upTrustScoreTypeName,
            String trustScoreTypeName,
            String trustGradeName,
            Integer score,
            String gubunCode,
            String deleteStatus,
            LocalDateTime createDate,
            LocalDateTime updateDate) {
        this.trustScoreTypeId = trustScoreTypeId;
        this.upTrustScoreTypeName = upTrustScoreTypeName;
        this.trustScoreTypeName = trustScoreTypeName;
        this.trustGradeName = trustGradeName;
        this.score = score;
        this.gubunCode = gubunCode;
        this.deleteStatus = deleteStatus;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
