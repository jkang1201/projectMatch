package com.example.demo.dto.projectmember.response;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.user.response.UserCrewDetailResponseDto;
import com.example.demo.model.project.ProjectMember;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberReadCrewDetailResponseDto {
    private Long projectMemberId;
    private Long projectId;
    private int projectCount;
    private UserCrewDetailResponseDto user;
    private Long projectMemberAuthId;
    private PositionResponseDto position;
    private ProjectMemberStatus status;

    public static ProjectMemberReadCrewDetailResponseDto of(
            ProjectMember projectMember,
            int projectCount,
            UserCrewDetailResponseDto user,
            PositionResponseDto position) {
        return ProjectMemberReadCrewDetailResponseDto.builder()
                .projectMemberId(projectMember.getId())
                .projectId(projectMember.getProject().getId())
                .projectCount(projectCount)
                .user(user)
                .projectMemberAuthId(projectMember.getProjectMemberAuth().getId())
                .position(position)
                .status(projectMember.getStatus())
                .build();
    }
}
