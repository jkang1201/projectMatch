package com.example.demo.model.trust_score;

import com.example.demo.global.common.BaseTimeEntity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * com.example.demo.model.trust_score.TrustScoreHistory
 *
 * <p>신뢰점수이력 Entity
 *
 * @author joo
 * @version 1.0
 * @since 2023/11/19
 *     <pre>
 * << 개정 이력 (Modification History) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------    ---------------------------
 *  2023/11/19     joo        최초 생성
 * </pre>
 */
@Entity
@Table(name = "trust_score_history")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Getter
public class TrustScoreHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trust_score_history_id")
    Long id;
    /** 사용자 자동생성 식별자 */
    @Column private Long userId;
    /** 신뢰점수타입 자동생성 식별자 */
    @Column private Long trustScoreTypeId;
    /** 프로젝트 자동생성 식별자 */
    @Column private Long projectId;
    /** 마일스톤 자동생성 식별자 */
    @Column private Long milestoneId;
    /** 업무 자동생성 식별자 */
    @Column private Long workId;
    /** 신뢰점수 증감 */
    @Column private int score;

    @Builder
    public TrustScoreHistory(
            Long userId,
            Long trustScoreTypeId,
            Long projectId,
            Long milestoneId,
            int score,
            Long workId) {
        this.userId = userId;
        this.trustScoreTypeId = trustScoreTypeId;
        this.projectId = projectId;
        this.milestoneId = milestoneId;
        this.workId = workId;
        this.score = score;
    }
}
