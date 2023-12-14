package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.CommonErrorCode;

public class CommonCustomException extends CustomException {

    public static final CommonCustomException INVALID_INPUT_VALUE =
            new CommonCustomException(CommonErrorCode.INVALID_INPUT_VALUE);

    public static final CommonCustomException DOES_NOT_EXIST_PARAMETER_IN_METHOD =
            new CommonCustomException(CommonErrorCode.DOES_NOT_EXIST_PARAMETER_IN_METHOD);

    public static final CommonCustomException INTERNAL_SERVER_ERROR =
            new CommonCustomException(CommonErrorCode.INTERNAL_SERVER_ERROR);

    public CommonCustomException(CommonErrorCode commonErrorCode) {
        super(commonErrorCode);
    }
}
