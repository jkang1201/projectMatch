package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.model.technology_stack.TechnologyStack;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

public interface TechnologyStackService {

    @Transactional(readOnly = true)
    ResponseDto<?> getTechnologyStackList();

    TechnologyStack findById(Long id);

    // 기술스택 PK 리스트로 기술스택 목록 조회
    List<TechnologyStack> findTechnologyStackListByIds(List<Long> techStackIds);
}
