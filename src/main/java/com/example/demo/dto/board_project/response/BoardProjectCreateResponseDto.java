package com.example.demo.dto.board_project.response;

import com.example.demo.dto.board.response.BoardCreateResponseDto;
import com.example.demo.dto.project.response.ProjectCreateResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectCreateResponseDto {
    private BoardCreateResponseDto board;
    private ProjectCreateResponseDto project;
}
