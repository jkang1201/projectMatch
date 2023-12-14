package com.example.demo.service.token;

import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenRedisService {

    private static final String KEY_PREFIX = "refreshToken:";

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${jwt.refresh.token.expiration.millis}")
    private long refreshTokenExpiresMillis;

    // Refresh Token 저장 (만료시간도 함께 설정)
    public void save(final Long userId, final String refreshToken) {
        String key = KEY_PREFIX + userId;

        ValueOperations<String, String> values = redisTemplate.opsForValue();
        values.set(key, refreshToken, refreshTokenExpiresMillis, TimeUnit.MILLISECONDS);
    }

    // Refresh Token 조회
    public String get(final Long userId) {
        String key = KEY_PREFIX + userId;
        return Optional.ofNullable(redisTemplate.opsForValue().get(key)).orElse(null);
    }

    // Refresh Token 삭제
    public void delete(Long userId) {
        String key = KEY_PREFIX + userId;

        if (redisTemplate.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }
}
