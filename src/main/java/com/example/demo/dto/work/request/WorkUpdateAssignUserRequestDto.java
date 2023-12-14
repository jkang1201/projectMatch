package com.example.demo.dto.work.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkUpdateAssignUserRequestDto {
    @NotNull(message = "업무 담당 유저는 필수입니다.")
    private Long assignUserId;
}
