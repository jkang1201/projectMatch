package com.example.demo.repository.trust_score;

public interface TrustScoreRepositoryCustom {
    void updateScore(Long userId, int score);

    int getUserScore(Long userId);

    void updateTrustGradeBatch();

    void updateUserTrustGrade(Long userId);
}
