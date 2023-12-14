package com.example.demo.repository.trust_score;

import com.example.demo.model.trust_score.TrustScore;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TrustScoreRepository
        extends JpaRepository<TrustScore, Long>, TrustScoreRepositoryCustom {
    boolean existsByUserId(Long userId);

    Optional<TrustScore> findTrustScoreByUserId(Long userId);

    @Modifying
    @Query(
            "update TrustScore ts set ts.score = (case when :score < 0 then 0 else :score end) where ts.userId = :userId")
    void updateUserTrustScore(@Param("userId") Long userId, @Param("score") int score);
}
