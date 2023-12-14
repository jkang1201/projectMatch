package com.example.demo.repository.trust_score;

import com.example.demo.dto.trust_score.ProjectUserHistoryDto;
import com.example.demo.model.trust_score.QTrustScoreHistory;
import com.example.demo.model.work.QWork;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TrustScoreHistoryRepositoryImpl implements TrustScoreHistoryRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;
    /**
     * 특정 프로젝트에 참여하는 사용자의, 해당 프로젝트 관련 발생한 신뢰점수이력을 DTO 형태로 반환
     *
     * @param projectId
     * @param userId
     * @return List<ProjectUserHistoryDto>
     */
    @Override
    public List<ProjectUserHistoryDto> getProjectUserHistory(Long projectId, Long userId) {
        QTrustScoreHistory trustScoreHistory = QTrustScoreHistory.trustScoreHistory;
        QWork work = QWork.work;
        return jpaQueryFactory
                .select(
                        Projections.constructor(
                                ProjectUserHistoryDto.class,
                                trustScoreHistory.workId,
                                trustScoreHistory.score,
                                work.completeStatus,
                                work.content,
                                trustScoreHistory.createDate))
                .from(trustScoreHistory)
                .where(
                        trustScoreHistory
                                .projectId
                                .eq(projectId)
                                .and(trustScoreHistory.userId.eq(userId)))
                .join(work)
                .on(trustScoreHistory.workId.eq(work.id))
                .fetch();
    }

    @Override
    public int calculateCurrentScore(Long userId) {
        QTrustScoreHistory trustScoreHistory = QTrustScoreHistory.trustScoreHistory;
        try {
            return jpaQueryFactory
                    .select(trustScoreHistory.score.sum())
                    .from(trustScoreHistory)
                    .where(trustScoreHistory.userId.eq(userId))
                    .fetchFirst();
        } catch (NullPointerException ne) {
            log.error("데이터가 존재하지 않음. userId : {}", userId);
            log.error("error :", ne);
            return 0;
        }
    }
}
