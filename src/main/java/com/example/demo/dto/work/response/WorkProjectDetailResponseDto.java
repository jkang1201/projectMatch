package com.example.demo.dto.work.response;

import com.example.demo.dto.user.response.UserProjectDetailResponseDto;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.work.Work;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WorkProjectDetailResponseDto {
    private Long workId;
    private UserProjectDetailResponseDto assginedUser;
    private ProjectMember lastModifiedMember;
    private String workContent;
    private Boolean expireStatus;
    private Boolean completeStatus;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static WorkProjectDetailResponseDto of(
            Work work, UserProjectDetailResponseDto userProjectDetailResponseDto) {
        return WorkProjectDetailResponseDto.builder()
                .workId(work.getId())
                .assginedUser(userProjectDetailResponseDto)
                .lastModifiedMember(work.getLastModifiedMember())
                .workContent(work.getContent())
                .expireStatus(work.isExpireStatus())
                .completeStatus(work.isCompleteStatus())
                .startDate(work.getStartDate())
                .endDate(work.getEndDate())
                .createDate(work.getCreateDate())
                .updateDate(work.getUpdateDate())
                .build();
    }
}
