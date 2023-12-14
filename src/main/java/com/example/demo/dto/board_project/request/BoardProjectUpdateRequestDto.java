package com.example.demo.dto.board_project.request;

import com.example.demo.dto.board.request.BoardUpdateRequestDto;
import com.example.demo.dto.project.request.ProjectUpdateRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardProjectUpdateRequestDto {
    private BoardUpdateRequestDto board;
    private ProjectUpdateRequestDto project;
}
