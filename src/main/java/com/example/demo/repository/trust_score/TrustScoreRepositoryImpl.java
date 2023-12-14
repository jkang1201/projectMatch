package com.example.demo.repository.trust_score;

import com.example.demo.model.trust_grade.QTrustGrade;
import com.example.demo.model.trust_score.QTrustScore;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrustScoreRepositoryImpl implements TrustScoreRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public void updateScore(Long userId, int score) {
        QTrustScore trustScore = QTrustScore.trustScore;
        jpaQueryFactory
                .update(trustScore)
                .set(
                        trustScore.score,
                        new CaseBuilder()
                                .when(trustScore.score.add(score).lt(0))
                                .then(0)
                                .otherwise(trustScore.score.add(score)))
                .where(trustScore.userId.eq(userId))
                .execute();
    }

    @Override
    public int getUserScore(Long userId) {
        QTrustScore trustScore = QTrustScore.trustScore;
        return jpaQueryFactory
                .select(trustScore.score)
                .from(trustScore)
                .where(trustScore.userId.eq(userId))
                .fetchFirst();
    }

    /** 0100AM 전체 유저 신뢰등급 업데이트 스케줄 */
    @Scheduled(cron = "0 1 * * *", zone = "Asia/Seoul")
    @Override
    public void updateTrustGradeBatch() {
        QTrustScore trustScore = QTrustScore.trustScore;
        QTrustGrade trustGrade = QTrustGrade.trustGrade;
        jpaQueryFactory
                .update(trustScore)
                .set(
                        trustScore.trustGrade,
                        JPAExpressions.selectFrom(trustGrade)
                                .where(
                                        trustScore
                                                .score
                                                .goe(trustGrade.minimumScore)
                                                .and(
                                                        trustScore.score.loe(
                                                                trustGrade.maximumScore))))
                .execute();
    }

    /**
     * 이력 발생시, 개별 유저 신뢰등급 업데이트
     *
     * @param userId
     */

    // TODO : 중복 코드 리팩토링
    public void updateUserTrustGrade(Long userId) {
        QTrustScore trustScore = QTrustScore.trustScore;
        QTrustGrade trustGrade = QTrustGrade.trustGrade;
        jpaQueryFactory
                .update(trustScore)
                .set(
                        trustScore.trustGrade,
                        JPAExpressions.selectFrom(trustGrade)
                                .where(
                                        trustScore
                                                .score
                                                .goe(trustGrade.minimumScore)
                                                .and(
                                                        trustScore.score.loe(
                                                                trustGrade.maximumScore))))
                .where(trustScore.userId.eq(userId))
                .execute();
    }
}
