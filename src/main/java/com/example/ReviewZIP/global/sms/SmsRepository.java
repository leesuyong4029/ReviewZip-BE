package com.example.ReviewZIP.global.sms;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class SmsRepository {
    private final String PREFIX = "sms:";
    private final int LIMIT_TIME = 3 * 60;

    private final StringRedisTemplate redisTemplate;

    public void createSmsCertification(String phoneNum, String certificationNum) {
        redisTemplate.opsForValue()
                .set(PREFIX + phoneNum, certificationNum, Duration.ofSeconds(LIMIT_TIME));
    }

    public String getSmsCertification(String phoneNum) {
        return redisTemplate.opsForValue().get(PREFIX + phoneNum);
    }

    public void removeSmsCertification(String phoneNum) {
        redisTemplate.delete(PREFIX + phoneNum);
    }

    public boolean hasKey(String phoneNum) {
        return redisTemplate.hasKey(PREFIX + phoneNum);
    }
}
