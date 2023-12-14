package com.example.demo.dto.projectmember.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectMemberReadTotalProjectCrewsResponseDto {
    private List<ProjectMemberReadProjectCrewsResponseDto> projectMembers;

    public static ProjectMemberReadTotalProjectCrewsResponseDto of(
            List<ProjectMemberReadProjectCrewsResponseDto> projectMembers) {
        return ProjectMemberReadTotalProjectCrewsResponseDto.builder()
                .projectMembers(projectMembers)
                .build();
    }
}
