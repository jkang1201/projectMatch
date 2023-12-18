package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.TokenErrorCode;

public class TokenCustomException extends CustomException {

    public static final TokenCustomException NON_ACCESS_TOKEN =
            new TokenCustomException(TokenErrorCode.NON_ACCESS_TOKEN);

    public static final TokenCustomException WRONG_TYPE_SIGNATURE =
            new TokenCustomException(TokenErrorCode.WRONG_TYPE_SIGNATURE);

    public static final TokenCustomException WRONG_TYPE_TOKEN =
            new TokenCustomException(TokenErrorCode.WRONG_TYPE_TOKEN);

    public static final TokenCustomException EXPIRED_TOKEN =
            new TokenCustomException(TokenErrorCode.EXPIRED_TOKEN);

    public static final TokenCustomException MALFORMED_TOKEN =
            new TokenCustomException(TokenErrorCode.MALFORMED_TOKEN);

    public static final TokenCustomException DOES_NOT_REFRESH_TOKEN =
            new TokenCustomException(TokenErrorCode.DOES_NOT_REFRESH_TOKEN);

    public static final TokenCustomException INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE =
            new TokenCustomException(TokenErrorCode.INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE);

    public static final TokenCustomException INVALID_REFRESH_TOKEN =
            new TokenCustomException(TokenErrorCode.INVALID_REFRESH_TOKEN);

    public static final TokenCustomException REFRESH_TOKEN_NOT_FOUND =
            new TokenCustomException(TokenErrorCode.REFRESH_TOKEN_NOT_FOUND);

    public TokenCustomException(TokenErrorCode tokenErrorCode) {
        super(tokenErrorCode);
    }
}
