package com.example.demo.dto.projectmember.response;

import com.example.demo.model.project.ProjectMemberAuth;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberAuthResponseDto {
    private Long projectMemberAuthId;
    private String projectMemberAuthName;
    private boolean milestone_change_YN;
    private boolean work_change_YN;

    public static ProjectMemberAuthResponseDto of(ProjectMemberAuth projectMemberAuth) {
        return ProjectMemberAuthResponseDto.builder()
                .projectMemberAuthId(projectMemberAuth.getId())
                .projectMemberAuthName(projectMemberAuth.getName())
                .milestone_change_YN(projectMemberAuth.isMilestoneChangeYN())
                .work_change_YN(projectMemberAuth.isWorkChangeYN())
                .build();
    }
}
