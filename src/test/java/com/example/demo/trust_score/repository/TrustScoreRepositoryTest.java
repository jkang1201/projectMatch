package com.example.demo.trust_score.repository;

import com.example.demo.global.exception.customexception.TrustGradeCustomException;
import com.example.demo.global.exception.customexception.TrustScoreCustomException;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.repository.trust_grade.TrustGradeRepository;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.repository.user.UserRepository;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TrustScoreRepositoryTest {
    @Autowired private TrustScoreRepository trustScoreRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private TrustGradeRepository trustGradeRepository;

    @Autowired private EntityManager entityManager;

    @Test
    @DisplayName("updateTrustGrade 메서드 테스트 - 성공")
    public void updateTrustGradeBatch_Method_Test_Pass() {
        // given
        // 유저 생성 및 저장
        User user = User.builder().nickname("테스트 사용자입니다").email("test@gmail.com").build();
        User saveUser = userRepository.save(user);
        TrustGrade trustGrade =
                trustGradeRepository
                        .findById(4L)
                        .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);
        TrustScore trustScore =
                TrustScore.builder()
                        .score(10000)
                        .userId(saveUser.getId())
                        .trustGrade(trustGrade)
                        .build();
        trustScoreRepository.save(trustScore);
        user.setTrustScore(trustScore);
        userRepository.save(saveUser);

        // when
        trustScoreRepository.updateTrustGradeBatch();
        entityManager.flush();
        entityManager.clear();

        // then
        TrustScore findTrustScore =
                trustScoreRepository
                        .findTrustScoreByUserId(saveUser.getId())
                        .orElseThrow(() -> TrustScoreCustomException.NOT_FOUND_TRUST_SCORE);
        Assertions.assertThat(findTrustScore.getTrustGrade().getName()).isEqualTo("1등급");
    }

    @Test
    @DisplayName("updateUserTrustGrade 메서드 테스트 - 성공")
    public void updateUserTrustGrade_Method_Test_Pass() {
        User user = User.builder().nickname("테스트 사용자입니다").email("test@gmail.com").build();
        User saveUser = userRepository.save(user);
        TrustGrade trustGrade =
                trustGradeRepository
                        .findById(4L)
                        .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);
        TrustScore trustScore =
                TrustScore.builder()
                        .score(10000)
                        .userId(saveUser.getId())
                        .trustGrade(trustGrade)
                        .build();
        trustScoreRepository.save(trustScore);
        user.setTrustScore(trustScore);
        userRepository.save(saveUser);

        // when
        trustScoreRepository.updateUserTrustGrade(saveUser.getId());
        entityManager.flush();
        entityManager.clear();

        // then
        TrustScore findTrustScore =
                trustScoreRepository
                        .findTrustScoreByUserId(saveUser.getId())
                        .orElseThrow(() -> TrustScoreCustomException.NOT_FOUND_TRUST_SCORE);
        Assertions.assertThat(findTrustScore.getTrustGrade().getName()).isEqualTo("1등급");
    }

    @Test
    @DisplayName("findByUserId 메서드 테스트 - 성공")
    public void findByUserId_Method_Test_Pass() {
        // given
        User user = User.builder().nickname("테스트 사용자입니다").email("test@gmail.com").build();
        User saveUser = userRepository.save(user);
        TrustScore trustScore = TrustScore.builder().score(10000).userId(saveUser.getId()).build();
        trustScoreRepository.save(trustScore);

        // when
        TrustScore findTrustScore =
                trustScoreRepository
                        .findTrustScoreByUserId(saveUser.getId())
                        .orElseThrow(() -> TrustScoreCustomException.NOT_FOUND_TRUST_SCORE);

        // then
        Assertions.assertThat(findTrustScore.getScore()).isEqualTo(10000);
    }
}
