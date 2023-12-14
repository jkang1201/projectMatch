package com.example.demo.dto.board.response;

import com.example.demo.model.board.Board;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardUpdateResponseDto {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private long boardPageView;
    private boolean boardCompleteStatus;
    private long boardUserId;
    private String boardContact;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static BoardUpdateResponseDto of(Board board) {
        return BoardUpdateResponseDto.builder()
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .boardPageView(board.getPageView())
                .boardCompleteStatus(board.isCompleteStatus())
                .boardUserId(board.getUser().getId())
                .boardContact(board.getContent())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
