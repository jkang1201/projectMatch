package com.example.demo.dto.position.response;

import com.example.demo.model.position.Position;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PositionResponseDto {
    private Long positionId;
    private String name;

    public static PositionResponseDto of(Position position) {
        return PositionResponseDto.builder()
                .positionId(position.getId())
                .name(position.getName())
                .build();
    }
}
