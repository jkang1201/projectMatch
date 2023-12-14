package com.example.demo.security;

import com.example.demo.dto.common.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/** 시큐리티 필터단에서 응답할 때, 공통으로 관리하기 위한 클래스 */
@Component
@RequiredArgsConstructor
public class SecurityResponseHandler {

    private final ObjectMapper objectMapper;

    // ResponseEntity 생성 및 반환
    public void sendResponse(
            HttpServletResponse response, HttpStatus httpStatus, ResponseDto responseValue)
            throws IOException {
        // 상태코드 설정
        response.setStatus(httpStatus.value());
        // 인코딩 타입 설정
        response.setCharacterEncoding("utf-8");
        // 컨텐트 타입 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 클라이언트로 응답 객체 전송
        response.getWriter().write(objectMapper.writeValueAsString(responseValue));
    }
}
