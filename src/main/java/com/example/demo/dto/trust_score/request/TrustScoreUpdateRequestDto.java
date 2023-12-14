package com.example.demo.dto.trust_score.request;

import com.example.demo.global.validation.annotation.ValidTrustScoreUpdateRequest;
import javax.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ValidTrustScoreUpdateRequest
@Builder
public class TrustScoreUpdateRequestDto {
    @NotNull(message = "사용자값은 필수입니다.")
    private Long userId;

    private Long projectId;
    private Long milestoneId;
    private Long workId;

    @NotNull(message = "점수타입값은 필수입니다.")
    private Long scoreTypeId;
}
