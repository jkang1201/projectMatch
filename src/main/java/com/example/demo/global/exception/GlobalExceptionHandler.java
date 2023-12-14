package com.example.demo.global.exception;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.customexception.CustomException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // @Valid 어노테이션 유효성 검사 실패 시 발생 Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDto<?>> processValidationError(
            MethodArgumentNotValidException e) {
        List<CustomFieldError> errors =
                e.getFieldErrors().stream()
                        .filter(fieldError -> fieldError != null)
                        .map(CustomFieldError::new)
                        .collect(Collectors.toList());

        final ResponseDto<List<CustomFieldError>> response =
                ResponseDto.fail("데이터 유효성 검사에 실패했습니다.", errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // CustomException
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDto<?>> customExceptionHandle(CustomException e) {
        final ResponseDto response = ResponseDto.fail(e.getErrorCode().getMessage());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(response);
    }
}
