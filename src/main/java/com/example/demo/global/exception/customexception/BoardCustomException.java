package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.BoardErrorCode;

public class BoardCustomException extends CustomException {

    public static final BoardCustomException NOT_FOUND_BOARD =
            new BoardCustomException(BoardErrorCode.NOT_FOUND_BOARD);

    public BoardCustomException(BoardErrorCode boardErrorCode) {
        super(boardErrorCode);
    }
}
