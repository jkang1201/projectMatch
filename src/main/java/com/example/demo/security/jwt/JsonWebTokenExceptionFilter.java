package com.example.demo.security.jwt;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.customexception.TokenCustomException;
import com.example.demo.global.exception.errorcode.ErrorCode;
import com.example.demo.security.SecurityResponseHandler;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT 관련 예외 발생 시, 예외를 처리하는 커스텀 필터 JsonWebTokenAuthenticationFilter 보다 먼저 수행되어 JWT 검증 및 인증 과정 중 발생하는
 * 예외를 처리
 */
@Slf4j
@RequiredArgsConstructor
public class JsonWebTokenExceptionFilter extends OncePerRequestFilter {

    private final SecurityResponseHandler securityResponseHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenCustomException tokenCustomException) {
            // CustomException ErrorCode
            ErrorCode errorCode = tokenCustomException.getErrorCode();

            // 클라이언트로 응답 전송
            securityResponseHandler.sendResponse(
                    response, errorCode.getStatus(), ResponseDto.fail(errorCode.getMessage()));
        }
    }
}
