package com.example.demo.global.exception.customexception;

import com.example.demo.global.exception.errorcode.TrustGradeErrorCode;

public class TrustGradeCustomException extends CustomException {
    public static final TrustGradeCustomException NOT_FOUND_TRUST_GRADE =
            new TrustGradeCustomException(TrustGradeErrorCode.NOT_FOUND_TRUST_GRADE);

    public TrustGradeCustomException(TrustGradeErrorCode trustGradeErrorCode) {
        super(trustGradeErrorCode);
    }
}
