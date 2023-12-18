package com.seojs.debateking.domain.chatRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Set;

@RequiredArgsConstructor
public class ChatRedisRepositoryImpl implements ChatRedisRepositoryCustom{
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void deleteByDebateRoomId(Long debateRoomId) {
        Set<Object> chats = redisTemplate.opsForZSet().range("chatSortedSet:" + debateRoomId, 0, -1);

        for (Object chat : chats) {
            redisTemplate.delete("chatRedis:" + (String) chat);
            redisTemplate.delete("chatRedis:" + (String) chat + ":idx");
        }

        redisTemplate.delete("chatRedis:debateRoomId:" + debateRoomId);
        redisTemplate.delete("chatSortedSet:" + debateRoomId);
    }
}
