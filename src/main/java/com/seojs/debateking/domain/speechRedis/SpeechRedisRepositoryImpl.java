package com.seojs.debateking.domain.speechRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@RequiredArgsConstructor
public class SpeechRedisRepositoryImpl implements SpeechRedisRepositoryCustom{
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void deleteByDebateRoomId(Long debateRoomId) {

        Set<Object> speeches = redisTemplate.opsForZSet().range("speechSortedSet:" + debateRoomId, 0, -1);

        for (Object speech : speeches) {
            redisTemplate.delete("speechRedis:" + (String) speech);
            redisTemplate.delete("speechRedis:" + (String) speech + ":idx");
        }

        redisTemplate.delete("speechRedis:debateRoomId:" + debateRoomId);
        redisTemplate.delete("speechSortedSet:" + debateRoomId);
    }
}
