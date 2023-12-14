package com.example.demo.security.custom;

import com.example.demo.dto.user.request.UserLoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * 회원을 인증하는 커스텀 필터 설정한 DEFAULT_LOGIN_REQUEST_URL 에서 해당 필터가 실행 인증 성공 시,
 * UserAuthenticationSuccessHandler 실행 인증 실패 시, UserAuthenticationFailureHandler 실행
 */
@Slf4j
public class UserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    // 로그인 요청 URL
    private static final String DEFAULT_LOGIN_REQUEST_URL = "/api/user/login";

    // 로그인 HTTP 메소드
    private static final String HTTP_METHOD = "POST";

    // "/api/user/login" + POST로 온 요청에 매칭
    private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher(DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);

    private final ObjectMapper objectMapper;

    public UserAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
        this.objectMapper = objectMapper;
    }

    // 로그인 요청 시 실행되는 로직
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException {

        // Request Body 에서 회원의 요청 데이터를 UserLoginRequestDto 타입으로 반환
        UserLoginRequestDto loginRequest =
                objectMapper.readValue(request.getInputStream(), UserLoginRequestDto.class);

        // 회원을 인증하기 위한 토큰생성
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(), loginRequest.getPassword());

        // 회원 인증 로직 수행
        return this.getAuthenticationManager().authenticate(token);
    }
}
