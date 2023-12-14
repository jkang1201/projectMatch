package com.example.demo.dto.trust_score_type;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TrustScoreTypeSearchCriteria {
    private Boolean isDeleted;
    private Boolean isParentType;
    private List<String> trustGrade;
    private List<Long> parentTypeId;
    private String gubunCode;
    private String keyword;
}
