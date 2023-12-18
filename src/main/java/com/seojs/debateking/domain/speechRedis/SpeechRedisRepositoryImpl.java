package com.seojs.debateking.domain.speechRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@RequiredArgsConstructor
public class SpeechRedisRepositoryImpl implements SpeechRedisRepositoryCustom{
    private final RedisTemplate<String, Object> redisTemplate;
    //private final RedisTemplate redisTemplate;

    @Override
    public void deleteByDebateRoomId(Long debateRoomId) {
        String keyPattern = "speechRedis:*";
        Set<String> keys = redisTemplate.keys(keyPattern);

        redisTemplate.delete(keys);
        //topic 지워야하나?

        redisTemplate.delete("speechSortedSet:" + debateRoomId);
    }
}
