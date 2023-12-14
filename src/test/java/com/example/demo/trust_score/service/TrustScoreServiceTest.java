package com.example.demo.trust_score.service;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;
import com.example.demo.model.milestone.Milestone;
import com.example.demo.model.project.Project;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.user.User;
import com.example.demo.model.work.Work;
import com.example.demo.service.trust_score.TrustScoreService;
import javax.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class TrustScoreServiceTest {
    @Autowired EntityManager entityManager;

    @Autowired TrustScoreService trustScoreService;

    @Test
    @DisplayName("4등급 프로젝트 업무완수")
    public void WorkComplete_FourthGrade_Test() {
        // 회원 생성 및 저장
        User user = User.builder().nickname("테스트회원").build();
        entityManager.persist(user);
        // 프로젝트 생성 및 저장
        TrustGrade trustGrade = entityManager.find(TrustGrade.class, 4L);
        Project project = Project.builder().name("테스트 프로젝트").trustGrade(trustGrade).build();
        entityManager.persist(project);
        // 마일스톤 생성 및 저장
        Milestone milestone = Milestone.builder().project(project).build();
        entityManager.persist(milestone);
        // 업무 생성 및 저장
        Work work =
                Work.builder()
                        .completeStatus(true)
                        .assignedUserId(user)
                        .project(project)
                        .milestone(milestone)
                        .build();
        entityManager.persist(work);
        // 신뢰점수 생성 및 저장
        TrustScore trustScore =
                TrustScore.builder()
                        .userId(user.getId())
                        .score(1000)
                        .trustGrade(trustGrade)
                        .build();
        entityManager.persist(trustScore);
        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();
        AddPointDto addPointDto =
                AddPointDto.builder()
                        .userId(user.getId())
                        .projectId(project.getId())
                        .milestoneId(milestone.getId())
                        .workId(work.getId())
                        .scoreTypeId(WORK_COMPLETE)
                        .build();

        // when
        TrustScoreUpdateResponseDto responseDto = trustScoreService.addPoint(addPointDto);

        // then
        Assertions.assertThat(responseDto.getScoreChange()).isEqualTo(20);
    }

    @Test
    @DisplayName("3등급 프로젝트 업무미흡")
    public void WorkIncomplete_FourthGrade_Test() {
        // 회원 생성 및 저장
        User user = User.builder().nickname("테스트회원").build();
        entityManager.persist(user);
        // 프로젝트 생성 및 저장
        TrustGrade trustGrade = entityManager.find(TrustGrade.class, 3L);
        Project project = Project.builder().name("테스트 프로젝트").trustGrade(trustGrade).build();
        entityManager.persist(project);
        // 마일스톤 생성 및 저장
        Milestone milestone = Milestone.builder().project(project).build();
        entityManager.persist(milestone);
        // 업무 생성 및 저장
        Work work =
                Work.builder()
                        .completeStatus(true)
                        .assignedUserId(user)
                        .project(project)
                        .milestone(milestone)
                        .build();
        entityManager.persist(work);
        // 신뢰점수 생성 및 저장
        TrustScore trustScore =
                TrustScore.builder()
                        .userId(user.getId())
                        .score(1000)
                        .trustGrade(trustGrade)
                        .build();
        entityManager.persist(trustScore);
        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();
        AddPointDto addPointDto =
                AddPointDto.builder()
                        .userId(user.getId())
                        .projectId(project.getId())
                        .milestoneId(milestone.getId())
                        .workId(work.getId())
                        .scoreTypeId(WORK_INCOMPLETE)
                        .build();

        // when
        TrustScoreUpdateResponseDto responseDto = trustScoreService.addPoint(addPointDto);

        // then
        Assertions.assertThat(responseDto.getScoreChange()).isEqualTo(-15);
    }

    @Test
    @DisplayName("신규가입")
    public void NewMember_Test() {
        // 회원 생성 및 저장
        User user = User.builder().nickname("테스트회원").build();
        entityManager.persist(user);

        AddPointDto addPointDto =
                AddPointDto.builder().userId(user.getId()).scoreTypeId(NEW_MEMBER).build();

        TrustScoreUpdateResponseDto responseDto = trustScoreService.addPoint(addPointDto);

        // then
        Assertions.assertThat(responseDto.getScoreChange()).isEqualTo(200);
    }

    @Test
    @DisplayName("2등급 프로젝트 탈퇴")
    public void SelfWithdrawal_SecondGrade_Test() {
        // 회원 생성 및 저장
        User user = User.builder().nickname("테스트회원").build();
        entityManager.persist(user);
        // 프로젝트 생성 및 저장
        TrustGrade trustGrade = entityManager.find(TrustGrade.class, 2L);
        Project project = Project.builder().name("테스트 프로젝트").trustGrade(trustGrade).build();
        entityManager.persist(project);
        // 신뢰점수 생성 및 저장
        TrustScore trustScore =
                TrustScore.builder()
                        .userId(user.getId())
                        .score(1000)
                        .trustGrade(trustGrade)
                        .build();
        entityManager.persist(trustScore);
        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();
        AddPointDto addPointDto =
                AddPointDto.builder()
                        .userId(user.getId())
                        .projectId(project.getId())
                        .scoreTypeId(SELF_WITHDRAWAL)
                        .build();

        // when
        TrustScoreUpdateResponseDto responseDto = trustScoreService.addPoint(addPointDto);

        // then
        Assertions.assertThat(responseDto.getScoreChange()).isEqualTo(-200);
    }

    @Test
    @DisplayName("1등급 프로젝트 강제 탈퇴")
    public void SelfWithdrawal_FirstGrade_Test() {
        // 회원 생성 및 저장
        User user = User.builder().nickname("테스트회원").build();
        entityManager.persist(user);
        // 프로젝트 생성 및 저장
        TrustGrade trustGrade = entityManager.find(TrustGrade.class, 1L);
        Project project = Project.builder().name("테스트 프로젝트").trustGrade(trustGrade).build();
        entityManager.persist(project);
        // 신뢰점수 생성 및 저장
        TrustScore trustScore =
                TrustScore.builder()
                        .userId(user.getId())
                        .score(1000)
                        .trustGrade(trustGrade)
                        .build();
        entityManager.persist(trustScore);
        // 영속성 컨텍스트 초기화
        entityManager.flush();
        entityManager.clear();
        AddPointDto addPointDto =
                AddPointDto.builder()
                        .userId(user.getId())
                        .projectId(project.getId())
                        .scoreTypeId(FORCE_WITHDRAWAL)
                        .build();

        // when
        TrustScoreUpdateResponseDto responseDto = trustScoreService.addPoint(addPointDto);

        // then
        Assertions.assertThat(responseDto.getScoreChange()).isEqualTo(-200);
    }
}
