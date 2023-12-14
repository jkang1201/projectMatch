package com.example.demo.dto.common;

import lombok.*;

@Getter
public class ResponseDto<T> {

    private String result;
    private String message;
    private T data;

    @Builder
    public ResponseDto(String result, String message, T data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    // 요청 성공 시, 응답 데이터가 없는 경우
    public static ResponseDto<Void> success(String message) {
        return ResponseDto.<Void>builder().result("success").message(message).build();
    }

    // 요청 성공 시, 응답 데이터가 있는 경우
    public static <T> ResponseDto<T> success(String message, T data) {
        return ResponseDto.<T>builder().result("success").message(message).data(data).build();
    }

    // 요청 실패 시, 응답 데이터가 없는 경우
    public static ResponseDto<Void> fail(String message) {
        return ResponseDto.<Void>builder().result("fail").message(message).build();
    }

    // 요청 실패 시, 응답 데이터가 있는 경우 (데이터 유효성 검사)
    public static <T> ResponseDto<T> fail(String message, T errors) {
        return ResponseDto.<T>builder().result("fail").message(message).data(errors).build();
    }
}
