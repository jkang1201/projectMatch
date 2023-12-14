package com.example.demo.global.exception.errorcode;

import org.springframework.http.HttpStatus;

public interface ErrorCode {

    HttpStatus getStatus();

    String getMessage();
}
