package com.example.demo.dto.board_project.request;

import com.example.demo.dto.board.request.BoardCreateRequestDto;
import com.example.demo.dto.project.request.ProjectCreateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectCreateRequestDto {
    private BoardCreateRequestDto board;
    private ProjectCreateRequestDto project;
}
