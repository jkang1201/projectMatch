package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum TokenErrorCode implements ErrorCode {
    NON_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "엑세스 토큰 정보가 존재하지 않습니다."),
    WRONG_TYPE_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 시그니처입니다."),
    WRONG_TYPE_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 형식이나 구성의 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰 정보입니다."),
    MALFORMED_TOKEN(HttpStatus.UNAUTHORIZED, "손상된 토큰 정보입니다."),
    DOES_NOT_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "요청 헤더 쿠키에 리프레시 토큰 정보가 존재하지 않습니다.");

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
