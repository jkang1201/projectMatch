package com.example.demo.service.position;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.position.response.PositionInfoResponseDto;
import com.example.demo.global.exception.customexception.PositionCustomException;
import com.example.demo.model.position.Position;
import com.example.demo.repository.position.PositionRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public ResponseDto<?> getPositionList() {
        List<PositionInfoResponseDto> positionList =
                positionRepository.findAll().stream()
                        .map(
                                position ->
                                        PositionInfoResponseDto.of(
                                                position.getId(), position.getName()))
                        .collect(Collectors.toList());

        return ResponseDto.success("포지션 목록 조회가 완료되었습니다.", positionList);
    }

    @Override
    public Position save(Position position) {
        return positionRepository.save(position);
    }

    @Override
    public Position findById(Long id) {
        return positionRepository
                .findById(id)
                .orElseThrow(() -> PositionCustomException.NOT_FOUND_POSITION);
    }
}
