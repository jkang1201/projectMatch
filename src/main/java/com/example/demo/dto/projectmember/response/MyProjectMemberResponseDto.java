package com.example.demo.dto.projectmember.response;

import com.example.demo.dto.user.response.UserMyProjectResponseDto;
import com.example.demo.model.project.ProjectMember;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyProjectMemberResponseDto {
    private Long projectMemberId;
    private UserMyProjectResponseDto user;

    public static MyProjectMemberResponseDto of(
            ProjectMember projectMember, UserMyProjectResponseDto userMyProjectResponseDto) {
        return MyProjectMemberResponseDto.builder()
                .projectMemberId(projectMember.getId())
                .user(userMyProjectResponseDto)
                .build();
    }
}
