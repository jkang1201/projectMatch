package com.example.demo.dto.work.request;

import com.example.demo.model.work.Work;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkReadResponseDto {
    private Long workId;
    private Long projectId;
    private Long milestoneId;
    private Long assignedUserId;
    private Long lastModifiedMember;
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private boolean expireStatus;
    private boolean completeStauts;

    public static WorkReadResponseDto of(Work work) {
        return WorkReadResponseDto.builder()
                .workId(work.getId())
                .projectId(work.getProject().getId())
                .milestoneId(work.getMilestone().getId())
                .assignedUserId(work.getAssignedUserId().getId())
                .lastModifiedMember(work.getLastModifiedMember().getId())
                .content(work.getContent())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .expireStatus(work.isExpireStatus())
                .completeStauts(work.isCompleteStatus())
                .build();
    }
}
