package com.example.demo.repository.trust_score;

import com.example.demo.model.trust_score.TrustScoreType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrustScoreTypeRepository
        extends JpaRepository<TrustScoreType, Long>, TrustScoreTypeRepositoryCustom {}
