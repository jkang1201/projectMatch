package com.example.demo.dto.position.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionInfoResponseDto {

    private Long positionId;

    private String positionName;

    public static PositionInfoResponseDto of(Long positionId, String positionName) {
        return PositionInfoResponseDto.builder()
                .positionId(positionId)
                .positionName(positionName)
                .build();
    }
}
