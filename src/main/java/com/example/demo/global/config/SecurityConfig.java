package com.example.demo.global.config;

import com.example.demo.security.SecurityResponseHandler;
import com.example.demo.security.custom.UserAuthenticationFailureHandler;
import com.example.demo.security.custom.UserAuthenticationFilter;
import com.example.demo.security.custom.UserAuthenticationSuccessHandler;
import com.example.demo.security.jwt.*;
import com.example.demo.service.token.RefreshTokenRedisService;
import com.example.demo.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Slf4j
@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JsonWebTokenProvider jsonWebTokenProvider;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final ObjectMapper objectMapper;
    private final CorsConfig corsConfig;
    private final SecurityResponseHandler securityResponseHandler;
    private final RefreshTokenRedisService refreshTokenRedisService;
    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 회원 인증 필터 빈 등록
    @Bean
    public UserAuthenticationFilter userAuthenticationFilter() throws Exception {
        UserAuthenticationFilter userAuthenticationFilter =
                new UserAuthenticationFilter(objectMapper);

        userAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        userAuthenticationFilter.setAuthenticationSuccessHandler(
                new UserAuthenticationSuccessHandler(
                        jsonWebTokenProvider, refreshTokenRedisService, securityResponseHandler));
        userAuthenticationFilter.setAuthenticationFailureHandler(
                new UserAuthenticationFailureHandler(securityResponseHandler));

        return userAuthenticationFilter;
    }

    @Bean
    public WebSecurityCustomizer configure() throws Exception {
        return (web) -> web.ignoring().antMatchers();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .cors()
                .configurationSource(corsConfig.corsConfigurationSource())
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin()
                .disable()
                .httpBasic()
                .disable();

        http.addFilterAfter(userAuthenticationFilter(), LogoutFilter.class);
        http.addFilterBefore(
                new JsonWebTokenLogoutFilter(refreshTokenRedisService, securityResponseHandler),
                UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(
                new JsonWebTokenAuthenticationFilter(jsonWebTokenProvider),
                JsonWebTokenLogoutFilter.class);
        http.addFilterBefore(
                new JsonWebTokenExceptionFilter(securityResponseHandler),
                JsonWebTokenAuthenticationFilter.class);
        http.addFilterBefore(
                new JsonWebTokenReissueFilter(refreshTokenRedisService, userService, objectMapper, jsonWebTokenProvider, securityResponseHandler),
                JsonWebTokenExceptionFilter.class);

        return http.build();
    }
}
