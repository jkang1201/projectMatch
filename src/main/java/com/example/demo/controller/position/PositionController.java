package com.example.demo.controller.position;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.position.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    // 포지션 목록 조회 요청
    @GetMapping("/api/position-list")
    public ResponseEntity<ResponseDto<?>> getPositionList() {
        return ResponseEntity.status(HttpStatus.OK).body(positionService.getPositionList());
    }
}
