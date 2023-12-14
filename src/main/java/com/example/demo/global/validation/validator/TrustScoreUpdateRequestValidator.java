package com.example.demo.global.validation.validator;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.global.validation.annotation.ValidTrustScoreUpdateRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
// TODO : 메시지 추가 고민

@Slf4j
@Component
public class TrustScoreUpdateRequestValidator
        implements ConstraintValidator<ValidTrustScoreUpdateRequest, TrustScoreUpdateRequestDto> {
    @Override
    public void initialize(ValidTrustScoreUpdateRequest constraintAnnotation) {}

    @Override
    public boolean isValid(TrustScoreUpdateRequestDto dto, ConstraintValidatorContext context) {
        Long scoreTypeId = dto.getScoreTypeId();

        boolean isValidApiScoreTypeId =
                scoreTypeId.equals(WORK_COMPLETE)
                        || scoreTypeId.equals(WORK_INCOMPLETE)
                        || scoreTypeId.equals(LATE_WORK);

        if (!isValidApiScoreTypeId) {
            log.info("잘못된 입력값. API 호출 가능하지 않은 신뢰점수타입 식별자. scoreTypeId : {}", scoreTypeId);
            return false;
        }

        return true;
    }
}
