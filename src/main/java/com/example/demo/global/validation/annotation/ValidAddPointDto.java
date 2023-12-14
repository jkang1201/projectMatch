package com.example.demo.global.validation.annotation;

import com.example.demo.global.validation.validator.AddPointDtoValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = AddPointDtoValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAddPointDto {
    String message() default "Invalid AddPointDto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
