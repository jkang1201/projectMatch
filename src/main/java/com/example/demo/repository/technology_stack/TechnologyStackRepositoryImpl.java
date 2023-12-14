package com.example.demo.repository.technology_stack;

import com.example.demo.global.exception.customexception.TechnologyStackCustomException;
import com.example.demo.model.technology_stack.QTechnologyStack;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class TechnologyStackRepositoryImpl implements TechnologyStackRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<TechnologyStack> findTechnologyStackListByIds(List<Long> techStackIds) {
        QTechnologyStack qTechnologyStack = QTechnologyStack.technologyStack;

        List<TechnologyStack> technologyStackList =
                jpaQueryFactory
                        .selectFrom(qTechnologyStack)
                        .where(qTechnologyStack.id.in(techStackIds))
                        .fetch();

        // 검증로직
        validateTechnologyStackExistence(techStackIds, technologyStackList);

        return technologyStackList;
    }

    // 요청한 기술스택 ID 목록과 조회한 기술스택 목록이 동일한지 검증
    private void validateTechnologyStackExistence(
            List<Long> techStackIds, List<TechnologyStack> foundTechnologyStacks) {
        List<Long> foundIds =
                foundTechnologyStacks.stream()
                        .map(TechnologyStack::getId)
                        .collect(Collectors.toList());

        List<Long> notFoundIds =
                techStackIds.stream()
                        .filter(id -> !foundIds.contains(id))
                        .collect(Collectors.toList());

        if (!notFoundIds.isEmpty()) {
            log.error("NOT_FOUND_ID : {}", notFoundIds);
            throw TechnologyStackCustomException.NOT_FOUND_TECHNOLOGY_STACK;
        }
    }
}
