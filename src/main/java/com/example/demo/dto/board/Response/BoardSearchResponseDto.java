package com.example.demo.dto.board.response;

import com.example.demo.dto.project.response.ProjectSearchResponseDto;
import com.example.demo.dto.user.response.UserSearchResponseDto;
import com.example.demo.model.board.Board;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardSearchResponseDto {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private ProjectSearchResponseDto project;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int boardPageView;
    private boolean boardCompleteStatus;
    private UserSearchResponseDto user;
    private String boardContact;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @QueryProjection
    public BoardSearchResponseDto(
            Long boardId,
            String boardTitle,
            String boardContent,
            ProjectSearchResponseDto project,
            LocalDateTime startDate,
            LocalDateTime endDate,
            int boardPageView,
            boolean boardCompleteStatus,
            UserSearchResponseDto user,
            String boardContact,
            LocalDateTime createDate,
            LocalDateTime updateDate) {
        this.boardId = boardId;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.project = project;
        this.startDate = startDate;
        this.endDate = endDate;
        this.boardPageView = boardPageView;
        this.boardCompleteStatus = boardCompleteStatus;
        this.user = user;
        this.boardContact = boardContact;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static BoardSearchResponseDto of(
            Board board,
            ProjectSearchResponseDto boardProjectSearchResponseDto,
            UserSearchResponseDto userSearchResponseDto) {
        return BoardSearchResponseDto.builder()
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .project(boardProjectSearchResponseDto)
                .boardPageView(board.getPageView())
                .boardCompleteStatus(board.isCompleteStatus())
                .user(userSearchResponseDto)
                .boardContent(board.getContent())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
