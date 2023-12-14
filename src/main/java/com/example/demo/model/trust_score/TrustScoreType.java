package com.example.demo.model.trust_score;

import com.example.demo.global.common.BaseTimeEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "trust_score_type")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
public class TrustScoreType extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trust_score_type_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "up_trust_score_type_id")
    private TrustScoreType upTrustScoreType; // Reference to the same entity

    @OneToMany(mappedBy = "upTrustScoreType")
    private List<TrustScoreType> subTrustScoreTypes = new ArrayList<>();

    /** 신뢰점수타입명 */
    @Column private String trustScoreTypeName;

    /** 신뢰등급명 */
    @Column private String trustGradeName;

    /** 신뢰점수 */
    @Column private Integer score;

    /** 구분코드 */
    @Column private String gubunCode;

    /** 삭제여부 */
    @Column private String deleteStatus;
}
