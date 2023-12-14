package com.example.demo.repository.trust_score;

import static com.example.demo.model.trust_score.QTrustScoreType.*;

import com.example.demo.dto.trust_score_type.TrustScoreTypeSearchCriteria;
import com.example.demo.dto.trust_score_type.response.TrustScoreTypeReadResponseDto;
import com.example.demo.model.project.QProject;
import com.example.demo.model.trust_grade.QTrustGrade;
import com.example.demo.model.trust_score.QTrustScore;
import com.example.demo.model.trust_score.QTrustScoreType;
import com.example.demo.model.trust_score.TrustScoreType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.*;
import com.querydsl.core.types.dsl.*;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class TrustScoreTypeRepositoryImpl implements TrustScoreTypeRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public int getScore(Long trustScoreTypeId) {
        // QClass 선언
        QTrustScoreType trustScoreType = QTrustScoreType.trustScoreType;
        // 조건 설정
        BooleanBuilder condition = new BooleanBuilder();
        condition.and(trustScoreType.id.eq(trustScoreTypeId));
        // 쿼리 수행
        return jpaQueryFactory
                .select(trustScoreType.score)
                .from(trustScoreType)
                .where(condition)
                .fetchFirst();
    }

    /**
     * project와 trustScoreType을 통해 요청에 맞는 신뢰점수 조회
     *
     * @param projectId
     * @param trustScoreTypeId
     * @return
     */
    @Override
    public int getScoreByProject(Long projectId, Long trustScoreTypeId) {
        QTrustScoreType trustScoreType = QTrustScoreType.trustScoreType;
        QTrustGrade trustGrade = QTrustGrade.trustGrade;
        QProject project = QProject.project;
        return jpaQueryFactory
                .select(
                        new CaseBuilder()
                                .when(trustScoreType.gubunCode.eq("M"))
                                .then(trustScoreType.score.multiply(-1))
                                .otherwise(trustScoreType.score))
                .from(project)
                .join(trustGrade)
                .on(trustGrade.id.eq(project.trustGrade.id))
                .join(trustScoreType)
                .on(
                        trustScoreType
                                .upTrustScoreType
                                .id
                                .eq(trustScoreTypeId)
                                .and(trustScoreType.trustGradeName.eq(trustGrade.name)))
                .where(project.id.eq(projectId))
                .fetchFirst();
    }
    // TODO : 두 메서드 통합
    @Override
    public List<Long> findAllUpScoreTypeId() {
        QTrustScoreType trustScoreType = QTrustScoreType.trustScoreType;
        return jpaQueryFactory
                .select(trustScoreType.id)
                .from(trustScoreType)
                .where(trustScoreType.upTrustScoreType.isNull())
                .fetch();
    }

    // TODO : 정렬 페이징 추가

    @Override
    public Page<TrustScoreTypeReadResponseDto> findSearchResults(
            TrustScoreTypeSearchCriteria criteria, Pageable pageable) {
        QTrustScoreType trustScoreType = QTrustScoreType.trustScoreType;
        QTrustScoreType subTrustScoreType = new QTrustScoreType("subTrustScoreType");
        JPAQuery<TrustScoreTypeReadResponseDto> query = jpaQueryFactory
                .select(
                        Projections.constructor(
                                TrustScoreTypeReadResponseDto.class,
                                trustScoreType.id,
                                getUpTrustScoreTypeName(trustScoreType),
                                trustScoreType.trustScoreTypeName,
                                getTrustGradeName(trustScoreType),
                                getScore(trustScoreType),
                                trustScoreType.gubunCode,
                                trustScoreType.deleteStatus,
                                trustScoreType.createDate,
                                trustScoreType.updateDate))
                .from(trustScoreType)
                .leftJoin(trustScoreType.upTrustScoreType, subTrustScoreType)
                .on(trustScoreType.upTrustScoreType.id.eq(subTrustScoreType.id))
                .where(
                        isDeleted(criteria.getIsDeleted()),
                        isParentType(criteria.getIsParentType()),
                        gubunCodeEq(criteria.getGubunCode()),
                        trustGradeContain(criteria.getTrustGrade()),
                        parentTypeIdContain(criteria.getParentTypeId()),
                        keywordLike(criteria.getKeyword())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(getOrderSpecifiers(pageable.getSort()));

        List<TrustScoreTypeReadResponseDto> list = query.fetch();

        long total = query.stream().count();

        return new PageImpl<>(list, pageable, total);

    }
    /** */
    @Override
    public void disableTrustScoreType(Long trustScoreTypeId) {
        jpaQueryFactory.update(trustScoreType)
                .set(trustScoreType.deleteStatus, "Y")
                .set(trustScoreType.updateDate, LocalDateTime.now())
                .where(trustScoreType.id.eq(trustScoreTypeId)
                        .or(trustScoreType.upTrustScoreType.isNotNull()
                        .and(trustScoreType.upTrustScoreType.id.eq(trustScoreTypeId))))
                .execute();
    }


    // TODO : 삼항연산자로 refactor
    private static Expression<String> getUpTrustScoreTypeName(QTrustScoreType trustScoreType) {
        if (trustScoreType.upTrustScoreType == null) {
            return new NullExpression<>(String.class);
        }
        return trustScoreType.upTrustScoreType.trustScoreTypeName;
    }

    private static Expression<String> getTrustGradeName(QTrustScoreType trustScoreType) {
        if (trustScoreType.trustGradeName == null) {
            return new NullExpression<>(String.class);
        }
        return trustScoreType.trustGradeName;
    }

    private static Expression<Integer> getScore(QTrustScoreType trustScoreType) {
        if (trustScoreType.score == null) {
            return new NullExpression<>(Integer.class);
        }
        return trustScoreType.score;
    }

    private BooleanExpression isDeleted(Boolean isDeleted) {
        if (isDeleted == null) {
            return null;
        }
        if (isDeleted) {
            return trustScoreType.deleteStatus.eq("Y");
        }
        return trustScoreType.deleteStatus.eq("N");
    }

    private BooleanExpression isParentType(Boolean isParentType) {
        if (isParentType == null) {
            return null;
        }
        if (isParentType) {
            return trustScoreType.upTrustScoreType.isNull();
        }
        return trustScoreType.upTrustScoreType.isNotNull();
    }

    private BooleanExpression gubunCodeEq(String gubunCode) {
        if (gubunCode == null) {
            return null;
        }
        return trustScoreType.gubunCode.eq(gubunCode);
    }

    private BooleanExpression trustGradeContain(List<String> trustGrade) {
        if (trustGrade == null || trustGrade.isEmpty()) {
            return null;
        }
        return trustScoreType.trustGradeName.in(trustGrade);
    }

    private BooleanExpression parentTypeIdContain(List<Long> parentTypeId) {
        if (parentTypeId == null || parentTypeId.isEmpty()) {
            return null;
        }
        BooleanExpression whenUpScoreTypeIsNull =
                trustScoreType.upTrustScoreType.isNull().and(trustScoreType.id.in(parentTypeId));
        BooleanExpression whenUpScoreTypeIsNotNull =
                trustScoreType
                        .upTrustScoreType
                        .isNotNull()
                        .and(trustScoreType.upTrustScoreType.id.in(parentTypeId));
        return whenUpScoreTypeIsNull.or(whenUpScoreTypeIsNotNull);
    }

    private BooleanExpression keywordLike(String keyword) {
        if (!StringUtils.hasText(keyword)) {
            return null;
        }
        return trustScoreType.trustScoreTypeName.contains(keyword);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        // Sort
        return sort.stream().map(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String property = order.getProperty();
            PathBuilder<TrustScoreType> path = new PathBuilder<>(trustScoreType.getType(), trustScoreType.getMetadata());
            Expression propertyExpression = path.get(property, TrustScoreType.class);
            return new OrderSpecifier(direction, propertyExpression);
        }).toArray(OrderSpecifier<?>[]::new);
    }
}