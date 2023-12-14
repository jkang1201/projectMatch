package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.TrustScoreErrorCode;

public class TrustScoreCustomException extends CustomException {

    public static final TrustScoreCustomException NOT_FOUND_TRUST_SCORE =
            new TrustScoreCustomException(TrustScoreErrorCode.NOT_FOUND_TRUST_SCORE);

    public TrustScoreCustomException(TrustScoreErrorCode trustScoreErrorCode) {
        super(trustScoreErrorCode);
    }
}
