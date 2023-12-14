package com.example.demo.global.config;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/** CORS 설정 파일 */
@Configuration
public class CorsConfig {

    // 배포 서버 주소
    private static final String DEPLOYED_SERVER_ORIGIN = "*";

    // 허용 HttpMethod 리스트
    private static final List<String> PERMIT_HTTP_METHOD =
            List.of("GET", "POST", "PUT", "PATCH", "DELETE");

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin(DEPLOYED_SERVER_ORIGIN);
        configuration.setAllowedMethods(PERMIT_HTTP_METHOD);
        configuration.addAllowedHeader("*");
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
