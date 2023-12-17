package com.seojs.debateking.domain.chatRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@RequiredArgsConstructor
public class ChatRedisRepositoryImpl implements ChatRedisRepositoryCustom{
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void deleteByDebateRoomId(Long debateRoomId) {
        String keyPattern = "chatRedis:*";
        Set<String> keys = redisTemplate.keys(keyPattern);

        redisTemplate.delete(keys);
        //topic 지워야하나?

        redisTemplate.delete("chatSortedSet:" + debateRoomId);
    }
}
