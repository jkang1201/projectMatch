package com.example.demo.dto.project.request;

import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectParticipateRequestDto {
    @NotNull(message = "포지션은 필수 입력 값입니다.")
    private Long positionId;
}
