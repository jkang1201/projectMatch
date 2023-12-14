package com.example.demo.controller.technology_stack;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.service.technology_stack.TechnologyStackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechnologyStackController {

    private final TechnologyStackService technologyStackService;

    // 기술스택 목록 조회 요청
    @GetMapping("/api/technology-stack-list")
    public ResponseEntity<ResponseDto<?>> getTechnologyStackList() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(technologyStackService.getTechnologyStackList());
    }
}
