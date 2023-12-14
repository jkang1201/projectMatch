package com.example.demo.dto.technology_stack.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TechnologyStackInfoResponseDto {

    private Long techStackId;

    private String techStackName;

    public static TechnologyStackInfoResponseDto of(Long techStackId, String techStackName) {
        return TechnologyStackInfoResponseDto.builder()
                .techStackId(techStackId)
                .techStackName(techStackName)
                .build();
    }
}
