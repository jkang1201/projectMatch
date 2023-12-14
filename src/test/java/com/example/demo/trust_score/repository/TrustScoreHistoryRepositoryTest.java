package com.example.demo.trust_score.repository;

import com.example.demo.dto.trust_score.ProjectUserHistoryDto;
import com.example.demo.model.project.Project;
import com.example.demo.model.trust_score.TrustScoreHistory;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.trust_score.TrustScoreHistoryRepository;
import com.example.demo.repository.user.UserRepository;
import com.example.demo.repository.work.WorkRepository;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TrustScoreHistoryRepositoryTest {

    @Autowired private TrustScoreHistoryRepository trustScoreHistoryRepository;

    @Autowired private UserRepository userRepository;

    @Autowired private WorkRepository workRepository;

    @Autowired private ProjectRepository projectRepository;

    @Test
    @DisplayName("신뢰점수 계산 - 성공")
    public void calculateCurrentScore_Method_Test_Pass() {
        // given
        // 테스트 유저 생성 및 저장
        User user = User.builder().email("testMail").intro("testIntro").build();
        User saveUser = userRepository.save(user);

        // 테스트 신뢰점수이력 생성 및 저장
        Long userId = saveUser.getId();
        TrustScoreHistory trustScoreHistory =
                TrustScoreHistory.builder().userId(userId).score(1000).trustScoreTypeId(1L).build();
        trustScoreHistoryRepository.save(trustScoreHistory);

        // when
        // 신뢰점수 계산
        int currentScore = trustScoreHistoryRepository.calculateCurrentScore(userId);

        // then
        Assertions.assertThat(currentScore).isEqualTo(1000);
    }

    @Test
    @DisplayName("신뢰점수 계산 - 실패")
    public void calculateCurrentScore_Method_Test_Fail() {
        // given
        // 테스트 유저 생성 및 저장
        User user = User.builder().email("testMail").intro("testIntro").build();
        User saveUser = userRepository.save(user);
        Long userId = saveUser.getId();

        // when
        // 신뢰점수 계산
        int currentScore = trustScoreHistoryRepository.calculateCurrentScore(userId);

        // then
        Assertions.assertThat(currentScore).isEqualTo(0);
    }

    @Test
    @DisplayName("프로젝트 사용자 이력 조회 ")
    public void getProjectUserHistory_Method_Test_Pass() {
        // given
        // 테스트 프로젝트 생성
        Project project = Project.builder().name("테스트 프로젝트입니다.").build();
        Project saveProject = projectRepository.save(project);
        Long projectId = saveProject.getId();

        // 테스트 업무 생성 및 저장
        Work testWork1 = Work.builder().content("테스트 업무입니다").completeStatus(true).build();

        Work testWork2 = Work.builder().content("테스트 업무입니다").completeStatus(false).build();
        Work saveWork1 = workRepository.save(testWork1);
        Work saveWork2 = workRepository.save(testWork2);

        // 테스트 유저 생성 및 저장
        User user = User.builder().email("testMail").intro("testIntro").build();
        User saveUser = userRepository.save(user);

        // 테스트 프로젝트 사용자 신뢰점수이력 생성 및 저장
        Long userId = saveUser.getId();
        TrustScoreHistory trustScoreHistory1 =
                TrustScoreHistory.builder()
                        .userId(userId)
                        .score(1000)
                        .trustScoreTypeId(1L)
                        .projectId(projectId)
                        .workId(saveWork1.getId())
                        .build();
        TrustScoreHistory trustScoreHistory2 =
                TrustScoreHistory.builder()
                        .userId(userId)
                        .score(1000)
                        .trustScoreTypeId(1L)
                        .projectId(projectId)
                        .workId(saveWork2.getId())
                        .build();
        trustScoreHistoryRepository.save(trustScoreHistory1);
        trustScoreHistoryRepository.save(trustScoreHistory2);

        // when
        List<ProjectUserHistoryDto> projectUserHistory =
                trustScoreHistoryRepository.getProjectUserHistory(projectId, userId);

        // then
        Assertions.assertThat(projectUserHistory.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("프로젝트 사용자 이력 조회 - 실패")
    public void getProjectUserHistory_Method_Test_Fail() {
        // given
        // 테스트 유저 생성 및 저장
        User user = User.builder().email("testMail").intro("testIntro").build();
        User saveUser = userRepository.save(user);
        Long userId = saveUser.getId();

        // when
        List<ProjectUserHistoryDto> projectUserHistory =
                trustScoreHistoryRepository.getProjectUserHistory(100L, userId);

        // then
        Assertions.assertThat(projectUserHistory).isEmpty();
    }
}
