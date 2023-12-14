package com.example.demo.repository.trust_score;

import com.example.demo.dto.trust_score.ProjectUserHistoryDto;
import java.util.List;

public interface TrustScoreHistoryRepositoryCustom {
    List<ProjectUserHistoryDto> getProjectUserHistory(Long projectId, Long userId);

    int calculateCurrentScore(Long userId);
}
