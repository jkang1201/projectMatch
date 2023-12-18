package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeCreateRequestDto;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeUpdateRequestDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeCreateResponseDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrustScoreTypeService {
    List<TrustScoreTypeReadResponseDto> getAllAndReturnDto();

    Page<TrustScoreTypeReadResponseDto> getSearchResults(TrustScoreTypeSearchCriteria criteria, Pageable pageable);

    TrustScoreTypeReadResponseDto findByIdAndReturnDto(Long trustScoreTypeId);

    TrustScoreTypeCreateResponseDto createTrustScoreType(TrustScoreTypeCreateRequestDto requestDto);
    void updateTrustScoreType(Long trustScoreTypeId, TrustScoreTypeUpdateRequestDto requestDto);
    void disableTrustScoreType(Long trustScoreTypeId);
}
