package com.seojs.debateking.service.speechRedis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, String> redisTemplate;
    private final JsonParser jsonParser;

    public void publish(ChannelTopic topic, Object object){
        redisTemplate.convertAndSend(topic.getTopic(), jsonParser.toJson(object));
    }
}
