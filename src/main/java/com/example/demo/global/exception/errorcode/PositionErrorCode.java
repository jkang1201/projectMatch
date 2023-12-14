package com.example.demo.global.exception.errorcode;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum PositionErrorCode implements ErrorCode {
    NOT_FOUND_POSITION(HttpStatus.NOT_FOUND, "해당 포지션이 존재하지 않습니다.");

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
