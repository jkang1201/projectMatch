package com.example.demo.service.trust_score;

import com.example.demo.dto.trust_score.AddPointDto;
import com.example.demo.dto.trust_score.response.TrustScoreUpdateResponseDto;
import com.example.demo.global.exception.customexception.TrustScoreCustomException;
import com.example.demo.model.trust_score.TrustScore;
import com.example.demo.model.trust_score.TrustScoreHistory;
import com.example.demo.repository.trust_score.TrustScoreHistoryRepository;
import com.example.demo.repository.trust_score.TrustScoreRepository;
import com.example.demo.repository.trust_score.TrustScoreTypeRepository;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class TrustScoreServiceImpl implements TrustScoreService {
    private final TrustScoreRepository trustScoreRepository;
    private final TrustScoreHistoryRepository trustScoreHistoryRepository;
    private final TrustScoreTypeRepository trustScoreTypeRepository;

    /**
     * DTO를 통한 신뢰점수 조회 및 포인트 부여
     *
     * @param addPointDto
     * @return TrustScoreUpdateResponseDto
     */
    @Override
    @Transactional
    public TrustScoreUpdateResponseDto addPoint(@Valid AddPointDto addPointDto) {
        Long userId = addPointDto.getUserId();

        // 요청에 맞는 신뢰점수 증감 조회
        int scoreChange = getScore(addPointDto);

        // 신뢰점수내역 추가
        createAndSaveHistory(addPointDto, scoreChange);

        // 신뢰점수내역 합산
        int calculatedScore = trustScoreHistoryRepository.calculateCurrentScore(userId);

        // 기존의 신뢰점수 테이블에 해당 유저에 대한 레코드가 없으면 생성, 있으면 업데이트
        if (!trustScoreRepository.existsByUserId(userId)) {
            trustScoreRepository.save(
                    TrustScore.builder().userId(userId).score(calculatedScore).build());
        } else {
            trustScoreRepository.updateUserTrustScore(userId, calculatedScore);
        }

        // 신뢰등급 변경
        trustScoreRepository.updateUserTrustGrade(userId);

        // 응답 DTO 생성 및 반환
        return TrustScoreUpdateResponseDto.builder()
                .userId(userId)
                .totalScore(calculatedScore)
                .scoreChange(scoreChange)
                .build();
    }

    @Override
    public TrustScore findTrustScoreByUserId(Long userId) {
        return trustScoreRepository
                .findTrustScoreByUserId(userId)
                .orElseThrow(() -> TrustScoreCustomException.NOT_FOUND_TRUST_SCORE);
    }

    /**
     * 신뢰점수이력 생성
     *
     * @param addPointDto, score
     * @return TrustScoreHistory
     */
    private TrustScoreHistory createAndSaveHistory(AddPointDto addPointDto, int score) {
        TrustScoreHistory history =
                TrustScoreHistory.builder()
                        .userId(addPointDto.getUserId())
                        .trustScoreTypeId(addPointDto.getScoreTypeId())
                        .projectId(addPointDto.getProjectId())
                        .milestoneId(addPointDto.getMilestoneId())
                        .workId(addPointDto.getWorkId())
                        .score(score)
                        .build();
        return trustScoreHistoryRepository.save(history);
    }
    /**
     * 신뢰점수 조회
     *
     * @param addPointDto
     * @return int
     */
    private int getScore(AddPointDto addPointDto) {
        if (addPointDto.getProjectId() == null) {
            return trustScoreTypeRepository.getScore(addPointDto.getScoreTypeId());
        }
        return trustScoreTypeRepository.getScoreByProject(
                addPointDto.getProjectId(), addPointDto.getScoreTypeId());
    }
}
