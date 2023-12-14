package com.example.demo.global.validation.annotation;

import com.example.demo.global.validation.validator.TrustScoreUpdateRequestValidator;
import java.lang.annotation.*;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = TrustScoreUpdateRequestValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTrustScoreUpdateRequest {
    String message() default "Invalid TrustScoreUpdateRequestDto";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
