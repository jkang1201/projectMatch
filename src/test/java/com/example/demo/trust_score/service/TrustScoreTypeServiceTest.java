package com.example.demo.trust_score.service;

import com.example.demo.constant.TrustScoreTypeIdentifier;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeCreateRequestDto;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeUpdateRequestDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeCreateResponseDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.global.exception.customexception.TrustScoreTypeCustomException;
import com.example.demo.model.trust_score.TrustScoreType;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import com.example.demo.service.trust_score.TrustScoreTypeService;
import java.util.List;
import javax.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static com.example.demo.constant.TrustScoreTypeIdentifier.WORK_INCOMPLETE;


@Transactional
@SpringBootTest
public class TrustScoreTypeServiceTest {

    @Autowired private TrustScoreTypeService trustScoreTypeService;

    @Autowired private TrustScoreTypeRepository trustScoreTypeRepository;

    @Test
    @DisplayName("신뢰점수타입 전체 DTO 반환")
    public void getAllAndReturnDto_Method_Test_Pass() {

        // when
        List<TrustScoreTypeReadResponseDto> getAllAndReturnDto =
                trustScoreTypeService.getAllAndReturnDto();

        // then
        Assertions.assertThat(getAllAndReturnDto.size()).isEqualTo(26);
    }

    @Test
    @DisplayName("개별 신뢰점수타입 DTO 반환")
    public void findByIdAndReturnDto_Method_Test_Pass() {
        // given
        // 테스트 신뢰점수타입 생성
        TrustScoreType trustScoreType =
                TrustScoreType.builder()
                        .upTrustScoreType(null)
                        .trustScoreTypeName("테스트 신뢰정보타입")
                        .trustGradeName("테스트 신뢰등급")
                        .gubunCode("테스트 구분코드")
                        .score(77777)
                        .deleteStatus("N")
                        .build();

        // when
        // 테스트 신뢰점수타입 저장 및 조회
        TrustScoreType saveTrustScoreType = trustScoreTypeRepository.save(trustScoreType);
        TrustScoreTypeReadResponseDto responseDto =
                trustScoreTypeService.findByIdAndReturnDto(saveTrustScoreType.getId());

        // then
        // 조회 신뢰점수타입 검증
        Assertions.assertThat(responseDto.getTrustScoreTypeId()).isGreaterThan(26);
        Assertions.assertThat(responseDto.getTrustScoreTypeName()).isEqualTo("테스트 신뢰정보타입");
    }

    @Test
    @DisplayName("상위신뢰점수타입 생성")
    public void createTrustScoreType_UpScoreType_Method_Test_Pass() {
        // given
        // 상위 신뢰점수타입 생성 DTO 생성
        TrustScoreTypeCreateRequestDto requestDto = new TrustScoreTypeCreateRequestDto();
        requestDto.setUpTrustScoreTypeId(null);
        requestDto.setScore(null);
        requestDto.setDeleteStatus("n");
        requestDto.setGubunCode("p");
        requestDto.setTrustGradeName(null);
        requestDto.setTrustScoreTypeName("테스트 신뢰점수타입");

        // when
        // 상위 신뢰점수타입 생성
        TrustScoreTypeCreateResponseDto responseDto =
                trustScoreTypeService.createTrustScoreType(requestDto);

        // then
        // 생성된 상위 신뢰점수타입 검증
        Assertions.assertThat(responseDto.getScore()).isNull();
        Assertions.assertThat(responseDto.getUpTrustScoreTypeName()).isNull();
        Assertions.assertThat(responseDto.getCreateDate()).isNotNull();
        Assertions.assertThat(responseDto.getId()).isGreaterThan(26);
        Assertions.assertThat(responseDto.getGubunCode()).isEqualTo("P");
        Assertions.assertThat(responseDto.getDeleteStatus()).isEqualTo("N");
    }

    @Test
    @DisplayName("하위신뢰점수타입 생성")
    public void createTrustScoreType_LowScoreType_Method_Test_Pass() {
        // given
        // 하위 신뢰점수타입 생성 DTO 생성
        TrustScoreTypeCreateRequestDto requestDto = new TrustScoreTypeCreateRequestDto();
        requestDto.setUpTrustScoreTypeId(WORK_INCOMPLETE);
        requestDto.setScore(700);
        requestDto.setDeleteStatus("n");
        requestDto.setGubunCode("m");
        requestDto.setTrustGradeName("테스트 신뢰등급");
        requestDto.setTrustScoreTypeName("테스트 신뢰등급 업무 미흡");

        // when
        // 하위 신뢰점수타입 생성
        TrustScoreTypeCreateResponseDto responseDto =
                trustScoreTypeService.createTrustScoreType(requestDto);

        // then
        // 생성된 하 신뢰점수타입 검증
        Assertions.assertThat(responseDto.getScore()).isEqualTo(700);
        Assertions.assertThat(responseDto.getUpTrustScoreTypeName()).isEqualTo("업무 미흡");
        Assertions.assertThat(responseDto.getCreateDate()).isNotNull();
        Assertions.assertThat(responseDto.getId()).isGreaterThan(26);
        Assertions.assertThat(responseDto.getGubunCode()).isEqualTo("M");
        Assertions.assertThat(responseDto.getDeleteStatus()).isEqualTo("N");
    }

    @Test
    @DisplayName("신뢰점수타입 수정")
    public void updateTrustScoreType_Test() {
        // given
        // 테스트 신뢰점수타입 생성
        TrustScoreTypeCreateRequestDto createRequestDto = new TrustScoreTypeCreateRequestDto();
        createRequestDto.setUpTrustScoreTypeId(WORK_INCOMPLETE);
        createRequestDto.setScore(700);
        createRequestDto.setDeleteStatus("n");
        createRequestDto.setGubunCode("m");
        createRequestDto.setTrustGradeName("테스트 신뢰등급");
        createRequestDto.setTrustScoreTypeName("테스트 신뢰등급 업무 미흡");

        TrustScoreTypeCreateResponseDto createResponseDto =
                trustScoreTypeService.createTrustScoreType(createRequestDto);
        Long id = createResponseDto.getId();


        // when
        // 테스트 신뢰점수타입 수정
        TrustScoreTypeUpdateRequestDto updateRequestDto = new TrustScoreTypeUpdateRequestDto();
        updateRequestDto.setDeleteStatus("Y");
        updateRequestDto.setScore(1000);
        updateRequestDto.setUpTrustScoreTypeId(WORK_INCOMPLETE);
        updateRequestDto.setTrustScoreTypeName("테스트 신뢰등급 업무 미흡");
        updateRequestDto.setTrustGradeName("테스트 신뢰등급");
        updateRequestDto.setGubunCode("P");

        trustScoreTypeService.updateTrustScoreType(id, updateRequestDto);
        TrustScoreType trustScoreType = trustScoreTypeRepository.findById(id).
                orElseThrow(() -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);

        // then
        // 수정사항 적용 여부 검증
        Assertions.assertThat(createResponseDto.getScore()).isNotEqualTo(trustScoreType.getScore());
        Assertions.assertThat(createResponseDto.getDeleteStatus()).isNotEqualTo(trustScoreType.getDeleteStatus());
    }
}
