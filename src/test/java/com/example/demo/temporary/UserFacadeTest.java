package com.example.demo.temporary;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.user.request.UserCreateRequestDto;
import com.example.demo.global.exception.customexception.TrustScoreCustomException;
import com.example.demo.global.exception.customexception.UserCustomException;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.service.user.UserFacade;
import java.util.List;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class UserFacadeTest {
    @Autowired private UserFacade userFacade;

    @Autowired private UserRepository userRepository;

    @Autowired private TrustScoreRepository trustScoreRepository;

    @Autowired private EntityManager entityManager;

    @Test
    @DisplayName("회원가입 테스트")
    public void User_TrustScore_Association_Test() {
        // given
        // UserCreateRequestDto 생성
        UserCreateRequestDto userCreateRequestDto =
                new UserCreateRequestDto(
                        "testmember@gmail.com",
                        "testpw!@#",
                        "테스트유저임",
                        1L,
                        List.of(1L),
                        "테스트 자기소개입니다.");

        // when
        // 회원가입 후 회원 조회
        ResponseDto<?> user = userFacade.createUser(userCreateRequestDto);
        Long userId = (Long) user.getData();
        entityManager.flush();
        entityManager.clear();
        User findUser =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        // then
        // 회원가입 기본등급 및 기본점수 확인
        Assertions.assertThat(findUser.getTrustScore().getTrustGrade().getName()).isEqualTo("4등급");
        Assertions.assertThat(findUser.getTrustScore().getScore()).isEqualTo(200);

        // when
        // 점수를 임의로 10,000으로 설정
        trustScoreRepository.updateUserTrustScore(userId, 10000);
        trustScoreRepository.updateUserTrustGrade(userId);
        entityManager.flush();
        entityManager.clear();
        TrustScore findTrustScore =
                trustScoreRepository
                        .findTrustScoreByUserId(userId)
                        .orElseThrow(() -> TrustScoreCustomException.NOT_FOUND_TRUST_SCORE);
        User againFindUser =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> UserCustomException.NOT_FOUND_USER);

        // then
        // 회원 객체 신뢰점수 및 신뢰등급 변경사항 적용 확인
        Assertions.assertThat(findTrustScore.getUserId()).isEqualTo(userId);
        Assertions.assertThat(findTrustScore.getTrustGrade().getName()).isEqualTo("1등급");
        Assertions.assertThat(againFindUser.getTrustScore().getTrustGrade().getName())
                .isEqualTo("1등급");
    }
}
