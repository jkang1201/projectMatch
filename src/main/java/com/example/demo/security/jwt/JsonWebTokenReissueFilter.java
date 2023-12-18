package com.example.demo.security.jwt;

import com.example.demo.dto.common.ResponseDto;
import com.example.demo.dto.user.request.UserTokenReissueRequestDto;
import com.example.demo.global.exception.customexception.CommonCustomException;
import com.example.demo.global.exception.customexception.CustomException;
import com.example.demo.global.exception.customexception.TokenCustomException;
import com.example.demo.global.exception.errorcode.ErrorCode;
import com.example.demo.security.SecurityResponseHandler;
import com.example.demo.security.custom.PrincipalDetails;
import com.example.demo.service.token.RefreshTokenRedisService;
import com.example.demo.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 *  AccessToken 만료 시, RefreshToken 값으로 AccessToken 재발급 로직을 담당하는 필터
 */
@RequiredArgsConstructor
public class JsonWebTokenReissueFilter extends OncePerRequestFilter {

    private static final String TOKEN_REISSUE_REQUEST_URI = "/api/user/token-reissue";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final UserService userService;
    private final ObjectMapper objectMapper;
    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final SecurityResponseHandler securityResponseHandler;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(isTokenReissueRequest(request)) {
            handleTokenReissue(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    // 토큰 재발급 요청 확인
    private boolean isTokenReissueRequest(HttpServletRequest request) {
        return request.getServletPath().equals(TOKEN_REISSUE_REQUEST_URI)
                && request.getMethod().equals(HttpMethod.POST.name());
    }

    // 토큰 재발급 로직
    private void handleTokenReissue(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            // 요청 데이터에서 회원 ID(PK) 추출
            Long userId = parseUserId(request);
            // 요청 데이터에서 RefreshToken 정보 추출
            String requestRefreshToken = parseRefreshToken(request);

            // 회원 ID(PK)로 회원 정보를 조회하고 커스텀 회원 인증 정보로 가져오기
            PrincipalDetails principalDetails = getUserByUserIdToPrincipalDetails(userId);

            // RefreshToken 검증
            validationRefreshToken(userId, requestRefreshToken);

            // 토큰 재발급
            JsonWebTokenDto newTokens = jsonWebTokenProvider.generateToken(principalDetails);

            // RefreshToken 갱신
            renewRefreshToken(userId, newTokens.getRefreshToken());

            // ResponseHeader 토큰 셋팅
            setTokensInResponseHeader(request, response, newTokens);

            // 성공 응답
            successTokenReissueResponse(response);
        } catch (CustomException customException) {
            // 실패 응답
            failTokenReissueResponse(response, customException.getErrorCode());
        }
    }

    // 요청 데이터에서 회원 ID(PK) 추출
    private Long parseUserId(HttpServletRequest request) throws IOException {
        try {
            UserTokenReissueRequestDto tokenReissueRequestData = objectMapper.readValue(request.getInputStream(), UserTokenReissueRequestDto.class);

            // Request 정보가 존재하지 않는 경우, 토큰 재발급을 위한 회원 식별 정보 부족 예외처리
            if(Objects.isNull(tokenReissueRequestData) || tokenReissueRequestData.getUserId() == null) {
                throw TokenCustomException.INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE;
            }

            return tokenReissueRequestData.getUserId();

        } catch (MismatchedInputException e) {
            throw CommonCustomException.INVALID_INPUT_VALUE;
        }
    }

    // 요청 데이터에서 RefreshToken 추출
    private String parseRefreshToken(HttpServletRequest request) {
        String refreshToken = jsonWebTokenProvider.resolveRefreshCookie(request).getValue();

        // 추출한 RefreshToken 정보가 null 또는 길이가 0인 경우, 토큰 재발급을 위한 회원 식별 정보 부족 예외처리
        if(!StringUtils.hasText(refreshToken)) {
            throw TokenCustomException.INSUFFICIENT_USER_IDENTIFICATION_FOR_TOKEN_REISSUE;
        }

        return refreshToken;
    }

    // RefreshToken 검증
    private void validationRefreshToken(Long userId, String requestRefreshToken) {
        // 회원 RefreshToken 조회
        String storedRefreshToken = refreshTokenRedisService.get(userId);

        // 회원의 RefreshToken 정보가 존재하지 않는 경우, 예외 처리
        if(storedRefreshToken == null) {
            throw TokenCustomException.REFRESH_TOKEN_NOT_FOUND;
        }

        // 요청한 RefreshToken, 저장된 회원의 RefreshToken 이 다른 경우, RefreshToken 예외 처리
        if(!requestRefreshToken.equals(storedRefreshToken)) {
            throw TokenCustomException.INVALID_REFRESH_TOKEN;
        }
    }

    // 회원 정보를 커스텀 회원 정보 객체로 조회
    private PrincipalDetails getUserByUserIdToPrincipalDetails(Long userId) {
        return new PrincipalDetails(userService.findById(userId));
    }

    // RefreshToken 갱신
    private void renewRefreshToken(Long userId, String newRefreshToken) {
        // 기존 RefreshToken 삭제
        refreshTokenRedisService.delete(userId);

        // 새로 발급받은 RefreshToken 추가
        refreshTokenRedisService.save(userId, newRefreshToken);
    }

    // ResponseHeader 토큰 셋팅
    private void setTokensInResponseHeader(HttpServletRequest request, HttpServletResponse response, JsonWebTokenDto tokens) {
        // RefreshToken 업데이트
        response.addCookie(updateRefreshTokenCookie(request, tokens.getRefreshToken()));
        // AccessToken 셋팅
        response.setHeader(AUTHORIZATION_HEADER, BEARER_TOKEN_PREFIX + tokens.getAccessToken());
    }

    // RefreshTokenCookie 업데이트
    private Cookie updateRefreshTokenCookie(HttpServletRequest request, String newRefreshToken) {
        Cookie refreshCookie = jsonWebTokenProvider.resolveRefreshCookie(request);

        refreshCookie.setValue(newRefreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setPath("/");

        return refreshCookie;
    }

    // 토큰 재발급 성공 응답
    private void successTokenReissueResponse(HttpServletResponse response) throws IOException{
        securityResponseHandler.sendResponse(response, HttpStatus.OK, ResponseDto.success("토큰 재발급이 완료되었습니다."));
    }

    // 토큰 재발급 실패 응답
    private void failTokenReissueResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException{
        securityResponseHandler.sendResponse(response, errorCode.getStatus(), ResponseDto.fail(errorCode.getMessage()));
    }
}
