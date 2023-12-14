package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum TrustScoreTypeErrorCode implements ErrorCode {
    NOT_FOUND_TRUST_SCORE_TYPE(HttpStatus.NOT_FOUND, "해당 식별자의 신뢰점수타입 정보가 존재하지 않습니다.");

    private HttpStatus status;
    private String message;

    @Override
    public HttpStatus getStatus() {
        return this.status;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
