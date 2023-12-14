package com.example.demo.service.trust_score;

import com.example.demo.constant.TrustScoreTypeIdentifier;
import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.request.TrustScoreUpdateRequestDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AddPointTest {

    @Autowired TrustScoreService trustScoreService;

    @Autowired private Validator validator;

    static Long userId = 1L;
    static Long projectId = 100L;
    static Long milestoneId = 50L;
    static Long workId = 150L;
    static Long scoreTypeId;

    @Before("")
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("1등급 프로젝트 업무 완수에 대한 테스트입니다.")
    void validWorkComplete() {
        scoreTypeId = TrustScoreTypeIdentifier.WORK_COMPLETE;
        AddPointDto addPointDto =
                new AddPointDto(userId, projectId, milestoneId, workId, scoreTypeId);
        TrustScoreUpdateResponseDto responseDto = trustScoreService.addPoint(addPointDto);
        Assertions.assertThat(responseDto.getScoreChange()).isEqualTo(30);
    }

    @Test
    @DisplayName("1등급 프로젝트 업무 미흡에 대한 테스트입니다.")
    void validWorkIncomplete() {
        scoreTypeId = TrustScoreTypeIdentifier.WORK_INCOMPLETE;
        AddPointDto addPointDto =
                new AddPointDto(userId, projectId, milestoneId, workId, scoreTypeId);
        TrustScoreUpdateResponseDto responseDto = trustScoreService.addPoint(addPointDto);
        Assertions.assertThat(responseDto.getScoreChange()).isEqualTo(-15);
    }

    @Test
    @DisplayName("유효하지 않은 업무 완수 요청에 대한 테스트입니다.")
    void invalidWorkComplete() {
        scoreTypeId = TrustScoreTypeIdentifier.WORK_COMPLETE;
        AddPointDto addPointDto = new AddPointDto(userId, null, milestoneId, workId, scoreTypeId);
        Set<ConstraintViolation<AddPointDto>> violations = validator.validate(addPointDto);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("유효하지 않은 신규멤버 입력값에 대한 테스트입니다.")
    void invalidNewMember() {
        scoreTypeId = TrustScoreTypeIdentifier.NEW_MEMBER;
        AddPointDto addPointDto = new AddPointDto(userId, null, milestoneId, workId, scoreTypeId);
        Set<ConstraintViolation<AddPointDto>> violations = validator.validate(addPointDto);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("유효한 신규멤버 입력값에 대한 테스트입니다.")
    void validNewMember() {
        scoreTypeId = TrustScoreTypeIdentifier.NEW_MEMBER;
        AddPointDto addPointDto = new AddPointDto(userId, null, milestoneId, workId, scoreTypeId);
        Set<ConstraintViolation<AddPointDto>> violations = validator.validate(addPointDto);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("프로젝트 탈퇴에 대한 유효성 검증 - 실패")
    void invalidWithdrawal() {
        scoreTypeId = TrustScoreTypeIdentifier.SELF_WITHDRAWAL;
        AddPointDto addPointDto = new AddPointDto(userId, 101L, milestoneId, workId, scoreTypeId);
        Set<ConstraintViolation<AddPointDto>> violations = validator.validate(addPointDto);
        Assertions.assertThat(violations).isNotEmpty();
    }

    @Test
    @DisplayName("신뢰점수 API Request DTO 유효성 검증 - 실패")
    void invalidTrustScoreUpdateRequestDto() {
        scoreTypeId = 10L;
        TrustScoreUpdateRequestDto requestDto = new TrustScoreUpdateRequestDto();
        requestDto.setScoreTypeId(scoreTypeId);
        Set<ConstraintViolation<TrustScoreUpdateRequestDto>> violations =
                validator.validate(requestDto);
        Assertions.assertThat(violations).isNotEmpty();
    }
}
