package com.example.demo.dto.project.response;

import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.dto.trust_grade.response.TrustGradeResponseDto;
import com.example.demo.model.project.Project;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectSearchResponseDto {
    private Long projectId;
    private String name;
    private String subject;
    private TrustGradeResponseDto trustGrade;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<TechnologyStackInfoResponseDto> technologyStacks;

    public static ProjectSearchResponseDto of(
            Project project,
            TrustGradeResponseDto trustGradeResponseDto,
            List<TechnologyStackInfoResponseDto> technologyStacks) {
        return ProjectSearchResponseDto.builder()
                .projectId(project.getId())
                .name(project.getName())
                .subject(project.getSubject())
                .trustGrade(trustGradeResponseDto)
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .technologyStacks(technologyStacks)
                .build();
    }
}
