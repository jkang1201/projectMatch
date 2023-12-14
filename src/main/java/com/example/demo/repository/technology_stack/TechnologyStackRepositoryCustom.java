package com.example.demo.repository.technology_stack;

import com.example.demo.model.technology_stack.TechnologyStack;
import java.util.List;

public interface TechnologyStackRepositoryCustom {

    // 요청한 기술스택 PK 리스트의 기술스택목록 조회
    List<TechnologyStack> findTechnologyStackListByIds(List<Long> techStackIds);
}
