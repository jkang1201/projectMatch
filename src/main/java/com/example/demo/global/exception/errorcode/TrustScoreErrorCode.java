package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum TrustScoreErrorCode implements ErrorCode {
    NOT_FOUND_TRUST_SCORE(HttpStatus.NOT_FOUND, "해당 회원의 신뢰점수 정보가 존재하지 않습니다.");

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
