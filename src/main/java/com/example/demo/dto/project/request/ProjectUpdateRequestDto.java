package com.example.demo.dto.project.request;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProjectUpdateRequestDto {
    @NotBlank(message = "프로젝트명은 필수 입력 값입니다.")
    private String name;

    @NotBlank(message = "프로젝트 주제명은 필수 입력 값입니다.")
    private String subject;

    @NotBlank(message = "프로젝트 신뢰등급은 필수 입력 값입니다.")
    private Long trustGradeId;

    @NotBlank(message = "프로젝트 인원 수는 필수 입력 값입니다.")
    private int crewNumber;

    @NotBlank(message = "시작날짜는 필수 입력 값입니다.")
    private LocalDateTime startDate;

    @NotBlank(message = "종료날짜는 필수 입력 값입니다.")
    private LocalDateTime endDate;

    @NotBlank(message = "모집분야는 필수 입력 값입니다.")
    private List<Long> technologyIds;
}
