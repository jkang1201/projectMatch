package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score_type.TrustScoreTypeBaseDto;
import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeCreateRequestDto;
import com.example.demo.dto.trust_score_type.request.TrustScoreTypeUpdateRequestDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeCreateResponseDto;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.global.exception.customexception.TrustScoreTypeCustomException;
import com.example.demo.model.trust_score.TrustScoreType;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrustScoreTypeServiceImpl implements TrustScoreTypeService {

    private final TrustScoreTypeRepository trustScoreTypeRepository;

    @Override
    public List<TrustScoreTypeReadResponseDto> getAllAndReturnDto() {
        List<TrustScoreType> findAllTrustScoreType = trustScoreTypeRepository.findAll();
        return findAllTrustScoreType.stream()
                .map(TrustScoreTypeReadResponseDto::of)
                .collect(Collectors.toList());
    }
    // TODO : 테스트
    @Override
    public Page<TrustScoreTypeReadResponseDto> getSearchResults(
            TrustScoreTypeSearchCriteria criteria, Pageable pageable) {
        return trustScoreTypeRepository.findSearchResults(criteria, pageable);
    }

    @Override
    public TrustScoreTypeReadResponseDto findByIdAndReturnDto(Long trustScoreTypeId) {
        TrustScoreType findTrustScoreType =
                trustScoreTypeRepository
                        .findById(trustScoreTypeId)
                        .orElseThrow(
                                () -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
        return TrustScoreTypeReadResponseDto.of(findTrustScoreType);
    }
    // TODO : 코드 리팩토링
    @Override
    public TrustScoreTypeCreateResponseDto createTrustScoreType(
            TrustScoreTypeCreateRequestDto requestDto) {

        TrustScoreType trustScoreType =
                TrustScoreType.builder()
                        .upTrustScoreType(getUpTrustScoreType(requestDto))
                        .trustScoreTypeName(requestDto.getTrustScoreTypeName())
                        .score(requestDto.getScore())
                        .gubunCode(requestDto.getGubunCode())
                        .deleteStatus(requestDto.getDeleteStatus())
                        .build();

        TrustScoreType saveTrustScoreType = trustScoreTypeRepository.save(trustScoreType);

        return TrustScoreTypeCreateResponseDto.builder()
                .id(saveTrustScoreType.getId())
                .upTrustScoreTypeName(
                        saveTrustScoreType.getUpTrustScoreType() == null
                                ? null
                                : saveTrustScoreType.getUpTrustScoreType().getTrustScoreTypeName())
                .trustGradeName(saveTrustScoreType.getTrustGradeName())
                .gubunCode(saveTrustScoreType.getGubunCode().toUpperCase())
                .score(saveTrustScoreType.getScore())
                .deleteStatus(saveTrustScoreType.getDeleteStatus().toUpperCase())
                .createDate(saveTrustScoreType.getCreateDate())
                .build();
    }
    @Override
    public void updateTrustScoreType(Long trustScoreTypeId, TrustScoreTypeUpdateRequestDto requestDto) {
        TrustScoreType trustScoreType = TrustScoreType.builder()
                .id(trustScoreTypeId)
                .upTrustScoreType(getUpTrustScoreType(requestDto))
                .trustScoreTypeName(requestDto.getTrustScoreTypeName())
                .trustGradeName(requestDto.getTrustGradeName())
                .score(requestDto.getScore())
                .gubunCode(requestDto.getGubunCode())
                .deleteStatus(requestDto.getDeleteStatus())
                .build();
        trustScoreTypeRepository.save(trustScoreType);
    }
    // TODO : 유효성 검사 : 사전 비활성화 상태 여부
    public void disableTrustScoreType(Long trustScoreTypeId) {
        if (!trustScoreTypeRepository.existsById(trustScoreTypeId)) {
            throw TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE;
        }
        trustScoreTypeRepository.disableTrustScoreType(trustScoreTypeId);
    }

    private TrustScoreType getUpTrustScoreType(TrustScoreTypeBaseDto requestDto) {
        if (requestDto.getUpTrustScoreTypeId() == null) {
            return null;
        }
        return trustScoreTypeRepository.findById(requestDto.getUpTrustScoreTypeId())
                .orElseThrow(() -> TrustScoreTypeCustomException.NOT_FOUND_TRUST_SCORE_TYPE);
    }
}
