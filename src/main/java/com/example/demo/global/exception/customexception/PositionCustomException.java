package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.PositionErrorCode;

public class PositionCustomException extends CustomException {

    public static final PositionCustomException NOT_FOUND_POSITION =
            new PositionCustomException(PositionErrorCode.NOT_FOUND_POSITION);

    public PositionCustomException(PositionErrorCode positionErrorCode) {
        super(positionErrorCode);
    }
}
