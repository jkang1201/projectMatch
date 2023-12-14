package com.example.demo.dto.milestone.request;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MileStoneUpdateRequestDto {
    private String content;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
