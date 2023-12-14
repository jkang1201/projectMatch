package com.example.demo.model.trust_score;

import com.example.demo.global.common.BaseTimeEntity;
import com.example.demo.model.trust_grade.TrustGrade;
import javax.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "trust_score")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class TrustScore extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trust_score_id")
    private Long id;

    /** 유저자동생성식별자 */
    @Column private Long userId;

    /** 유저신뢰점수값 */
    @Column private int score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trust_grade_id")
    private TrustGrade trustGrade;

    @Builder
    public TrustScore(Long userId, int score) {
        this.userId = userId;
        this.score = score;
    }
}
