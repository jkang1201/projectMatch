package com.example.demo.security.jwt;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.security.SecurityResponseHandler;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.token.RefreshTokenRedisService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 로그아웃을 담당하는 커스텀 필터 로그아웃 로직도 로그인 된 상태에서 실행되어야 하므로, JsonWebTokenLogoutFilter 동작 전에
 * JsonWebTokenAuthenticationFilter 동작을 통해 사용자 인증 과정 후 로그아웃 로직을 실행할 수 있음
 */
@Slf4j
@RequiredArgsConstructor
public class JsonWebTokenLogoutFilter extends OncePerRequestFilter {

    private static final String DEFAULT_LOGOUT_REQUEST_URI = "/api/user/logout";
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final SecurityResponseHandler securityResponseHandler;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 로그아웃 요청일 경우
        if (isLogoutRequest(request)) {
            handleLogout(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    // 로그아웃 요청 확인
    private boolean isLogoutRequest(HttpServletRequest request) {
        return request.getServletPath().equals(DEFAULT_LOGOUT_REQUEST_URI)
                && request.getMethod().equals(HttpMethod.POST.name());
    }

    // 로그아웃 로직 수행
    private void handleLogout(HttpServletResponse response) throws IOException {
        // 시큐리티 컨텍스트에서 사용자 인증 정보 가져오기
        PrincipalDetails principalDetails =
                (PrincipalDetails)
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("user_id : {}", principalDetails.getId());

        // RefreshToken 삭제
        deleteRefreshToken(principalDetails.getId());

        // 현재 사용자의 인증 정보 삭제
        SecurityContextHolder.clearContext();

        // 쿠키에서 RefreshToken 삭제
        expirationRefreshCookie(response);

        // 클라이언트로 응답 전송
        securityResponseHandler.sendResponse(
                response, HttpStatus.OK, ResponseDto.success("로그아웃이 완료되었습니다."));
    }

    // Redis 에서 회원의 RefreshToken 삭제
    private void deleteRefreshToken(Long userId) {
        if (refreshTokenRedisService.get(userId) != null) {
            refreshTokenRedisService.delete(userId);
        }
    }

    // Refresh Cookie 만료시키기
    private void expirationRefreshCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("Refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
