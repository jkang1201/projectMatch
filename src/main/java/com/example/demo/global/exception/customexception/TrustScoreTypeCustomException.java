package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.TrustScoreTypeErrorCode;

public class TrustScoreTypeCustomException extends CustomException {

    public static final TrustScoreTypeCustomException NOT_FOUND_TRUST_SCORE_TYPE =
            new TrustScoreTypeCustomException(TrustScoreTypeErrorCode.NOT_FOUND_TRUST_SCORE_TYPE);

    public TrustScoreTypeCustomException(TrustScoreTypeErrorCode trustScoreTypeErrorCode) {
        super(trustScoreTypeErrorCode);
    }
}
