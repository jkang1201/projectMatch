package com.example.demo.dto.work.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkUpdateRequestDto {
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
