package com.example.demo.repository.trust_score;

import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TrustScoreTypeRepositoryCustom {
    /**
     * 프로젝트가 아닌 경우 점수조회
     *
     * @param trustScoreTypeId
     * @return
     */
    int getScore(Long trustScoreTypeId);

    /**
     * 프로젝트에 해당하는 점수 조회
     *
     * @param trueScoreTypeId
     * @return
     */
    int getScoreByProject(Long projectId, Long trueScoreTypeId);

    /**
     * 신뢰점수타입 대분류 아이디 조회
     *
     * @return List<Long>
     */
    List<Long> findAllUpScoreTypeId();

    Page<TrustScoreTypeReadResponseDto> findSearchResults(TrustScoreTypeSearchCriteria criteria, Pageable pageable);
    /** 신뢰점수타입 비활성화 */
    void disableTrustScoreType(Long trustScoreTypeId);
}
