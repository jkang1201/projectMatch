package com.example.demo.dto.board_project.response;

import com.example.demo.dto.board.response.BoardUpdateResponseDto;
import com.example.demo.dto.project.response.ProjectUpdateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectUpdateResponseDto {
    private BoardUpdateResponseDto board;
    private ProjectUpdateResponseDto project;
}
