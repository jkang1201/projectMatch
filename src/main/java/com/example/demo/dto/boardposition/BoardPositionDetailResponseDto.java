package com.example.demo.dto.boardposition;

import com.example.demo.dto.position.response.PositionResponseDto;
import com.example.demo.model.board.BoardPosition;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardPositionDetailResponseDto {
    private Long boardPositionId;
    private PositionResponseDto position;

    public static BoardPositionDetailResponseDto of(
            BoardPosition boardPosition, PositionResponseDto positionResponseDto) {
        return BoardPositionDetailResponseDto.builder()
                .boardPositionId(boardPosition.getId())
                .position(positionResponseDto)
                .build();
    }
}
