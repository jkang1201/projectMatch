package com.example.demo.dto.trust_score;

import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.global.validation.annotation.ValidAddPointDto;
import lombok.Builder;
import lombok.Data;

@Data
@ValidAddPointDto
public class AddPointDto {
    private Long userId;
    private Long projectId;
    private Long milestoneId;
    private Long workId;
    private Long scoreTypeId;

    public AddPointDto(TrustScoreUpdateRequestDto requestDto) {
        this.userId = requestDto.getUserId();
        this.projectId = requestDto.getProjectId();
        this.milestoneId = requestDto.getMilestoneId();
        this.workId = requestDto.getWorkId();
        this.scoreTypeId = requestDto.getScoreTypeId();
    }

    @Builder
    public AddPointDto(
            Long userId, Long projectId, Long milestoneId, Long workId, Long scoreTypeId) {
        this.userId = userId;
        this.projectId = projectId;
        this.milestoneId = milestoneId;
        this.workId = workId;
        this.scoreTypeId = scoreTypeId;
    }
}
