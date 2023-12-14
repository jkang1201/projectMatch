package com.example.demo.dto.project.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectConfirmRequestDto {
    @NotNull(message = "포지션은 필수 입력 값입니다.")
    private Long positionId;
}
