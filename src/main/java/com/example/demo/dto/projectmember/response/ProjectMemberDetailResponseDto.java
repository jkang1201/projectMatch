package com.example.demo.dto.projectmember.response;

import com.example.demo.constant.ProjectMemberStatus;
import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.dto.user.response.UserProjectDetailResponseDto;
import com.example.demo.model.project.ProjectMember;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberDetailResponseDto {
    private Long projectMemberId;
    private UserProjectDetailResponseDto user;
    private ProjectMemberAuthResponseDto projectMemberAuth;
    private PositionResponseDto position;
    private ProjectMemberStatus status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ProjectMemberDetailResponseDto of(
            ProjectMember projectMember,
            UserProjectDetailResponseDto user,
            ProjectMemberAuthResponseDto projectMemberAuth,
            PositionResponseDto position) {
        return ProjectMemberDetailResponseDto.builder()
                .projectMemberId(projectMember.getId())
                .user(user)
                .projectMemberAuth(projectMemberAuth)
                .position(position)
                .status(projectMember.getStatus())
                .createDate(projectMember.getCreateDate())
                .updateDate(projectMember.getUpdateDate())
                .build();
    }
}
