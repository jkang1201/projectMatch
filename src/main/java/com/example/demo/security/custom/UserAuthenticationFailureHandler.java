package com.example.demo.security.custom;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.global.exception.errorcode.UserErrorCode;
import com.example.demo.security.SecurityResponseHandler;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * UserAuthenticationFilter 에서 사용자 인증 실패 시 실행되는 핸들러 UsernameNotFoundException OR
 * BadCredentialsException 발생 시 커스텀 예외를 응답
 */
@Slf4j
@RequiredArgsConstructor
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    // 회원 인증 실패 시 응답 할 커스텀 ErrorCode
    private static final UserErrorCode INVALID_AUTHENTICATION =
            UserErrorCode.INVALID_AUTHENTICATION;
    private final SecurityResponseHandler securityResponseHandler;

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception)
            throws IOException {
        // 클라이언트로 응답 전송
        securityResponseHandler.sendResponse(
                response,
                INVALID_AUTHENTICATION.getStatus(),
                ResponseDto.fail(INVALID_AUTHENTICATION.getMessage()));
    }
}
