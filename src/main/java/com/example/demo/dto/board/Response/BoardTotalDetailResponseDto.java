package com.example.demo.dto.board.response;

import com.example.demo.dto.project.response.ProjectDetailResponseDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardTotalDetailResponseDto {
    private BoardDetailResponseDto board;
    private ProjectDetailResponseDto project;

    public static BoardTotalDetailResponseDto of(
            BoardDetailResponseDto boardDetailResponseDto,
            ProjectDetailResponseDto projectDetailResponseDto) {
        return BoardTotalDetailResponseDto.builder()
                .board(boardDetailResponseDto)
                .project(projectDetailResponseDto)
                .build();
    }
}
