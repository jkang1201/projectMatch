package com.example.demo.dto.project.response;

import com.example.demo.constant.ProjectStatus;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.model.project.Project;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectSpecificDetailResponseDto {
    private Long projectId;
    private String name;
    private String subject;
    private TrustGradeResponseDto trustGrade;
    private ProjectStatus status;
    private int crewNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static ProjectSpecificDetailResponseDto of(
            Project project, TrustGradeResponseDto trustGradeDto) {
        return ProjectSpecificDetailResponseDto.builder()
                .projectId(project.getId())
                .name(project.getName())
                .subject(project.getSubject())
                .trustGrade(trustGradeDto)
                .status(project.getStatus())
                .crewNumber(project.getCrewNumber())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .createDate(project.getCreateDate())
                .updateDate(project.getUpdateDate())
                .build();
    }
}
