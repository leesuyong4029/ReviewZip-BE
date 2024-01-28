package com.example.ReviewZIP.global.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    public void addHashtag(String query) {
        HashOperations<String, String, String> hashOperations = redisTemplate.opsForHash();

        if (hashOperations.hasKey("hashtags", query)) {
            hashOperations.increment("hashtags", query, 1);
        } else {
            hashOperations.put("hashtags", query, "1");
        }
    }

}
