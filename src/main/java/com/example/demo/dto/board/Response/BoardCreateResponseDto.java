package com.example.demo.dto.board.response;

import com.example.demo.model.board.Board;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardCreateResponseDto {
    private String boardTitle;
    private String boardContent;
    private long boardPageView;
    private boolean boardCompleteStatus;
    private long boardUserId;
    private String boardContact;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static BoardCreateResponseDto of(Board board) {
        return BoardCreateResponseDto.builder()
                .boardTitle(board.getTitle())
                .boardContent(board.getContent())
                .boardPageView(board.getPageView())
                .boardCompleteStatus(board.isCompleteStatus())
                .boardUserId(board.getUser().getId())
                .boardContact(board.getContact())
                .createDate(board.getCreateDate())
                .updateDate(board.getUpdateDate())
                .build();
    }
}
