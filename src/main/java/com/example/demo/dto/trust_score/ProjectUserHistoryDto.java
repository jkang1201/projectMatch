package com.example.demo.dto.trust_score;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectUserHistoryDto {
    Long id;
    Boolean completeStatus;
    String content;
    Integer trustScore;
    LocalDateTime createDate;

    public ProjectUserHistoryDto(
            Long workId,
            Integer score,
            Boolean completeStatus,
            String content,
            LocalDateTime createDate) {
        this.id = workId;
        this.trustScore = score;
        this.completeStatus = completeStatus;
        this.content = content;
        this.createDate = createDate;
    }
}
