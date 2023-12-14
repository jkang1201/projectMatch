package com.example.demo.service.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.technology_stack.response.TechnologyStackInfoResponseDto;
import com.example.demo.global.exception.customexception.TechnologyStackCustomException;
import com.example.demo.model.technology_stack.TechnologyStack;
import com.example.demo.repository.technology_stack.TechnologyStackRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TechnologyStackServiceImpl implements TechnologyStackService {

    private final TechnologyStackRepository technologyStackRepository;

    @Override
    public ResponseDto<?> getTechnologyStackList() {
        List<TechnologyStackInfoResponseDto> techStackList =
                technologyStackRepository.findAll().stream()
                        .map(
                                technologyStack ->
                                        TechnologyStackInfoResponseDto.of(
                                                technologyStack.getId(), technologyStack.getName()))
                        .collect(Collectors.toList());

        return ResponseDto.success("기술스택 목록 조회가 완료되었습니다.", techStackList);
    }

    @Override
    public TechnologyStack findById(Long id) {
        return technologyStackRepository
                .findById(id)
                .orElseThrow(() -> TechnologyStackCustomException.NOT_FOUND_TECHNOLOGY_STACK);
    }

    @Override
    public List<TechnologyStack> findTechnologyStackListByIds(List<Long> techStackIds) {
        return technologyStackRepository.findTechnologyStackListByIds(techStackIds);
    }
}
