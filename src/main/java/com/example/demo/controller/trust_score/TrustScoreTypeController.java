package com.example.demo.controller.trust_score;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeCreateRequestDto;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeUpdateRequestDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeCreateResponseDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.service.trust_score.TrustScoreTypeService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TrustScoreTypeController {
    private final TrustScoreTypeService trustScoreTypeService;
    // TODO : 코드 리팩토링, 두 메서드 통합
    /** 신뢰점수타입 관리자 초기 화면 */
    @GetMapping("/api/trust-score-type")
    public ResponseEntity<ResponseDto<?>> getAll() {
        List<TrustScoreTypeReadResponseDto> dto = trustScoreTypeService.getAllAndReturnDto();
        return new ResponseEntity<>(ResponseDto.success("success", dto), HttpStatus.OK);
    }
    /** 신규 신뢰점수타입 생성 */
    @GetMapping("/api/trust-score-type/search")
    public ResponseEntity<ResponseDto<?>> getSearchResults(
            @RequestParam(name = "isDeleted", required = false) Boolean isDeleted,
            @RequestParam(name = "isParentType", required = false) Boolean isParentType,
            @RequestParam(name = "trustGrade", required = false) List<String> trustGrade,
            @RequestParam(name = "parentTypeId", required = false) List<Long> parentTypeId,
            @RequestParam(name = "gubunCode", required = false) String gubunCode,
            @RequestParam(name = "keyword", required = false) String keyword,
            @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        TrustScoreTypeSearchCriteria criteria =
                TrustScoreTypeSearchCriteria.builder()
                        .isParentType(isDeleted)
                        .isParentType(isParentType)
                        .trustGrade(trustGrade)
                        .parentTypeId(parentTypeId)
                        .gubunCode(gubunCode)
                        .keyword(keyword)
                        .build();
        Page<TrustScoreTypeReadResponseDto> searchResults =
                trustScoreTypeService.getSearchResults(criteria);
        return new ResponseEntity<>(ResponseDto.success("success", searchResults), HttpStatus.OK);
    }

    /** 개별 신뢰점수타입 상세 조회 */
    @GetMapping("/api/trust-score-type/{trustScoreTypeId}")
    public ResponseEntity<ResponseDto<?>> getSearchResults(
            @PathVariable(name = "trustScoreTypeId") Long trustScoreTypeId) {
        TrustScoreTypeReadResponseDto responseDto =
                trustScoreTypeService.findByIdAndReturnDto(trustScoreTypeId);
        return new ResponseEntity<>(ResponseDto.success("success", responseDto), HttpStatus.OK);
    }

    /** 신규 신뢰점수타입 생성 */
    @PostMapping("/api/trust-score-type")
    public ResponseEntity<ResponseDto<?>> createTrustScoreType(
            @RequestBody @Valid TrustScoreTypeCreateRequestDto requestDto) {
        TrustScoreTypeCreateResponseDto responseDto = trustScoreTypeService.createTrustScoreType(requestDto);

        return new ResponseEntity<>(ResponseDto.success("success", responseDto), HttpStatus.OK);
    }
    /** 개별 신뢰점수타입 수정 TODO : 상위신뢰점수타입을 수정한다면? */
    @PutMapping("/api/trust-score-type/{trustScoreTypeId}")
    public ResponseEntity<ResponseDto<?>> updateTrustScoreType(
            @PathVariable(name = "trustScoreTypeId") Long trustScoreTypeId,
            @RequestBody @Valid TrustScoreTypeUpdateRequestDto requestDto) {
        trustScoreTypeService.updateTrustScoreType(trustScoreTypeId, requestDto);
        return new ResponseEntity<>(ResponseDto.success("success", null), HttpStatus.OK);
    }
    /** 신뢰점수타입 논리적 삭제 */
    // TODO : 유효성 검사 trustScoreTypeId
    @DeleteMapping("/api/trust-score-type/{trustScoreTypeId}/disable")
    public ResponseEntity<ResponseDto<?>> disableTrustSCoreType(
            @PathVariable(name = "trustScoreTypeId") Long trustScoreTypeId) {
        trustScoreTypeService.disableTrustScoreType(trustScoreTypeId);
        return new ResponseEntity<>(ResponseDto.success("success", null), HttpStatus.OK);
    }
}
