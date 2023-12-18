package com.example.demo.trust_score.repository;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.global.exception.customexception.TrustGradeCustomException;
import com.example.demo.global.exception.customexception.TrustScoreTypeCustomException;
import com.example.demo.model.project.Project;
import com.example.demo.model.trust_grade.TrustGrade;
import com.example.demo.model.trust_score.TrustScoreType;
import com.example.demo.repository.project.ProjectRepository;
import com.example.demo.repository.trust_grade.TrustGradeRepository;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

import static com.example.demo.constant.TrustScoreTypeIdentifier.*;

@SpringBootTest
@Transactional
public class TrustScoreTypeRepositoryTest {

    @Autowired private TrustScoreTypeRepository trustScoreTypeRepository;

    @Autowired private TrustGradeRepository trustGradeRepository;

    @Autowired private ProjectRepository projectRepository;

    @Test
    @DisplayName("getScore 메서드 테스트 - 성공")
    public void getScore_MethodTest_Pass() {
        // given
        Long scoreTypeId = NEW_MEMBER;

        // when
        int score = trustScoreTypeRepository.getScore(scoreTypeId);

        // then
        Assertions.assertThat(score).isEqualTo(200);
    }

    @Test
    @DisplayName("getScore 메서드 테스트 - 실패. " + "원인 : 존재하지 않는 신뢰점수타입 식별자")
    public void getScore_MethodTest_Fail() {
        // given
        Long scoreTypeId = 1000000L;

        // when - then
        Assertions.assertThatThrownBy(() -> trustScoreTypeRepository.getScore(scoreTypeId))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("getScoreByProject 메서드 테스트 - 성공")
    public void getScoreByProject_MethodTest_Pass() {
        // given
        // 4등급 프로젝트 생성
        TrustGrade trustGrade =
                trustGradeRepository
                        .findById(4L)
                        .orElseThrow(() -> TrustGradeCustomException.NOT_FOUND_TRUST_GRADE);
        Project project = Project.builder().name("테스트 프로젝트").trustGrade(trustGrade).build();
        Project saveProject = projectRepository.save(project);

        // when
        Long trustScoreTypeId = WORK_COMPLETE;
        int scoreByProject =
                trustScoreTypeRepository.getScoreByProject(saveProject.getId(), trustScoreTypeId);

        // then
        Assertions.assertThat(scoreByProject).isEqualTo(20);
    }

    @Test
    @DisplayName("getScoreByProject 메서드 테스트 - 실패 " + "원인 : 해당 프로젝트 존재하지 않음")
    public void getScoreByProject_MethodTest_Fail() {
        // given
        Long projectId = 10000000000000L;
        Long trustScoreTypeId = WORK_COMPLETE;

        // when - then
        Assertions.assertThatThrownBy(
                        () ->
                                trustScoreTypeRepository.getScoreByProject(
                                        projectId, trustScoreTypeId))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    @DisplayName("findAllUpScoreTypeId 메서드 테스트 - 성공")
    public void findAllUpScoreTypeId_MethodTest_Pass() {
        // given
        List<Long> scoreTypeIdentifierList =
                new ArrayList<>(
                        Arrays.asList(
                                WORK_COMPLETE,
                                WORK_INCOMPLETE,
                                NEW_MEMBER,
                                SELF_WITHDRAWAL,
                                FORCE_WITHDRAWAL,
                                LATE_WORK));

        // when
        List<Long> upScoreTypeIdList = trustScoreTypeRepository.findAllUpScoreTypeId();

        // then
        Assertions.assertThat(scoreTypeIdentifierList).isEqualTo(upScoreTypeIdList);
    }
    // TODO : 더 나은 테스트 방식 고민
    @Test
    @DisplayName("신뢰점수타입 전체 조회")
    public void findAllTrustScoreTypes_Method_Test_Pass() {
        // when
        List<TrustScoreType> allTrustScoreTypes = trustScoreTypeRepository.findAll();

        // then
        Assertions.assertThat(allTrustScoreTypes.size()).isEqualTo(26);
    }
    // TODO : 더 나은 테스트 방식 고민
    @Test
    @DisplayName("신뢰점수타입 참조 작동 여부 확인")
    public void UpTrustScoreType_Contains_Children_MappedBy_Test() {
        // given
        TrustScoreType parent =
                trustScoreTypeRepository
                        .findById(WORK_COMPLETE)
                        .orElseThrow(
                                () -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);

        // when
        List<TrustScoreType> children = parent.getSubTrustScoreTypes();

        // then
        Assertions.assertThat(children.size()).isEqualTo(4);
    }

    @Test
    @DisplayName("검색 조건 없음 - 전체 조회")
    public void getSearchResults_NoCriteria_Pass() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();
        PageRequest pageable = PageRequest.of(1, 10);
        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("검색 결과 없음")
    public void getSearchResults_NewMember_firstTrustGrade_NoResult_Pass() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();
        List<Long> newMember = Collections.singletonList(3L);
        List<String> firstTrustGrade = Collections.singletonList("1등급");
        criteria.setParentTypeId(newMember);
        criteria.setTrustGrade(firstTrustGrade);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults).isEmpty();
    }

    @Test
    @DisplayName("상위신뢰점수타입 조회")
    public void getSearchResults_isParentType() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();
        criteria.setIsParentType(true);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("구분코드가 P 신뢰점수타입 조회")
    public void getSearchResults_gubunCodePlus() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();
        criteria.setGubunCode("P");
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("1등급, 2등급 신뢰점수타입 조회")
    public void getSearchResults_FirstAndSecondGrade_Test_Pass() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();
        List<String> grades = new ArrayList<>();
        grades.add("1등급");
        grades.add("2등급");
        criteria.setTrustGrade(grades);
        Pageable pageable = PageRequest.of(0, 10);
        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("1등급, 2등급 및 신규회원, 강제탈퇴 신뢰점수타입 조회")
    public void getSearchResults_FirstAndSecondGrade_NewMemberAndForceWithdrawal_Test_Pass() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();

        List<String> grades = new ArrayList<>();
        grades.add("1등급");
        grades.add("2등급");
        criteria.setTrustGrade(grades);

        List<Long> ids = new ArrayList<>();
        ids.add(NEW_MEMBER);
        ids.add(FORCE_WITHDRAWAL);
        criteria.setParentTypeId(ids);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("1등급, 2등급 및 신규회원, 강제탈퇴 신뢰점수타입 조회")
    public void getSearchResults_NewMemberAndForceWithdrawal_Test_Pass() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();

        List<Long> ids = new ArrayList<>();
        ids.add(NEW_MEMBER);
        ids.add(FORCE_WITHDRAWAL);
        criteria.setParentTypeId(ids);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(6);
    }

    @Test
    @DisplayName("신뢰등급명 검색 키워드를 통한 조회")
    public void getSearchResults_Keyword_WorkComplete() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();
        String keyword = "업무 완수";
        criteria.setKeyword(keyword);
        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(5);
    }

    @Test
    @DisplayName("신뢰등급명과 신뢰등급을 통한 조회")
    public void getSearchResults_KeywordAndTrustGrade_WorkComplete() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();

        String keyword = "업무 완수";
        String trustGrade1 = "1등급";
        String trustGrade2 = "2등급";

        List<String> trustGradeList = new ArrayList<>();
        trustGradeList.add(trustGrade1);
        trustGradeList.add(trustGrade2);

        criteria.setKeyword(keyword);
        criteria.setTrustGrade(trustGradeList);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("검색 키워드 공백 조회")
    public void getSearchResults_KeywordWhiteSpace() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();

        String emptyKeyword = "  ";
        criteria.setKeyword(emptyKeyword);

        Pageable pageable = PageRequest.of(0, 10);

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(10);
    }

    @Test
    @DisplayName("상위신뢰점수타입 비활성화")
    public void disableTrustScoreType_UpTrustScoreType() {
        // given
        Long trustScoreTypeId = WORK_INCOMPLETE;
        Long downTrustScoreTypeId = 12L; //  2등급 업무 미흡

        // when
        trustScoreTypeRepository.disableTrustScoreType(trustScoreTypeId);

        // then
        TrustScoreType findTrustScoreType =
                trustScoreTypeRepository.findById(trustScoreTypeId)
                        .orElseThrow(() -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
        Assertions.assertThat(findTrustScoreType.getDeleteStatus()).isEqualTo("Y");

        TrustScoreType findTrustScoreType2 =
                trustScoreTypeRepository.findById(trustScoreTypeId)
                        .orElseThrow(() -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
        Assertions.assertThat(findTrustScoreType2.getDeleteStatus()).isEqualTo("Y");


    }
    @Test
    @DisplayName("하위신뢰점수타입 비활성화")
    public void disableTrustScoreType_DownTrustScoreType() {
        // given
        Long trustScoreTypeId = 13L; // 4등급 WORK_INCOMPLETE

        // when
        trustScoreTypeRepository.disableTrustScoreType(trustScoreTypeId);

        // then
        TrustScoreType findTrustScoreType =
                trustScoreTypeRepository.findById(trustScoreTypeId)
                        .orElseThrow(() -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
        Assertions.assertThat(findTrustScoreType.getDeleteStatus()).isEqualTo("Y");

    }

    @Test
    @DisplayName("신뢰점수타입 기본 페이지")
    public void getPagedResults() {
        // given
        TrustScoreTypeSearchCriteria criteria = new TrustScoreTypeSearchCriteria();
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));

        // when
        Page<TrustScoreTypeReadResponseDto> searchResults
                = trustScoreTypeRepository.findSearchResults(criteria, pageable);

        // then
        Assertions.assertThat(searchResults).isNotNull();
        Assertions.assertThat(searchResults.getContent().size()).isEqualTo(10);



    }
}
