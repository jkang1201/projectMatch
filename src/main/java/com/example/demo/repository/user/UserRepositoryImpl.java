package com.example.demo.repository.user;

import com.example.demo.model.position.QPosition;
import com.example.demo.model.technology_stack.QTechnologyStack;
import com.example.demo.model.trust_grade.QTrustGrade;
import com.example.demo.model.trust_score.QTrustScore;
import com.example.demo.model.user.QUser;
import com.example.demo.model.user.QUserTechnologyStack;
import com.example.demo.model.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public User fetchUserDetailsForUpdate(Long userId) {
        QUser qUser = QUser.user;
        QUserTechnologyStack qUserTechnologyStack = QUserTechnologyStack.userTechnologyStack;
        QTechnologyStack qTechnologyStack = QTechnologyStack.technologyStack;

        return jpaQueryFactory
                .selectFrom(qUser)
                .leftJoin(qUser.techStacks, qUserTechnologyStack)
                .fetchJoin()
                .leftJoin(qUserTechnologyStack.technologyStack, qTechnologyStack)
                .fetchJoin()
                .where(qUser.id.eq(userId))
                .fetchOne();
    }

    @Override
    public User fetchUserByUserIdWithAllAttributes(Long userId) {
        QUser qUser = QUser.user;
        QPosition qPosition = QPosition.position;
        QUserTechnologyStack qUserTechnologyStack = QUserTechnologyStack.userTechnologyStack;
        QTechnologyStack qTechnologyStack = QTechnologyStack.technologyStack;
        QTrustScore qTrustScore = QTrustScore.trustScore;
        QTrustGrade qTrustGrade = QTrustGrade.trustGrade;

        return jpaQueryFactory
                .selectFrom(qUser)
                .leftJoin(qUser.position, qPosition).fetchJoin()
                .leftJoin(qUser.techStacks, qUserTechnologyStack).fetchJoin()
                .leftJoin(qUserTechnologyStack.technologyStack, qTechnologyStack).fetchJoin()
                .leftJoin(qUser.trustScore, qTrustScore).fetchJoin()
                .leftJoin(qTrustScore.trustGrade, qTrustGrade).fetchJoin()
                .where(qUser.id.eq(userId))
                .fetchOne();
    }
}
