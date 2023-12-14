package com.example.demo.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
@AllArgsConstructor
public class CustomFieldError {

    private String field;
    private String invalidValue;
    private String reason;

    public CustomFieldError(FieldError fieldError) {
        this.field = fieldError.getField();
        this.invalidValue =
                fieldError.getRejectedValue() != null
                        ? fieldError.getRejectedValue().toString()
                        : null;
        this.reason = fieldError.getDefaultMessage();
    }
}
