package com.example.demo.dto.work.request;

import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.project.ProjectMember;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkCreateRequestDto {
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Long assignedUserId;

    public Work toWorkEntity(
            Project project, Milestone milestone, User user, ProjectMember projectMember) {
        return Work.builder()
                .project(project)
                .milestone(milestone)
                .assignedUserId(user)
                .lastModifiedMember(projectMember)
                .content(this.getContent())
                .expireStatus(false)
                .completeStatus(false)
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .build();
    }
}
