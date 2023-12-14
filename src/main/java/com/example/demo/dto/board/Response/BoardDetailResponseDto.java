package com.example.demo.dto.board.response;

import com.example.demo.dto.boardposition.BoardPositionDetailResponseDto;
import com.example.demo.dto.user.response.UserBoardDetailResponseDto;
import com.example.demo.model.board.Board;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardDetailResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private int pageView;
    private boolean compeleteStatus;
    private UserBoardDetailResponseDto user;
    private String contact;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private List<BoardPositionDetailResponseDto> boardPositions;

    public static BoardDetailResponseDto of(
            Board board,
            UserBoardDetailResponseDto userBoardDetailResponseDto,
            List<BoardPositionDetailResponseDto> boardPositionDetailResponseDtos) {
        return BoardDetailResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .pageView(board.getPageView())
                .compeleteStatus(board.isCompleteStatus())
                .user(userBoardDetailResponseDto)
                .contact(board.getContact())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .boardPositions(boardPositionDetailResponseDtos)
                .build();
    }
}
