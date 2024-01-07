package com.seojs.debateking.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class RedisMessageListener {
    private static final Map<Long, ChannelTopic> TOPICS = new HashMap<>();
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisSubscriber redisSubscriber;

    public void enterChatRoom(Long debateRoomId){
        ChannelTopic topic = getTopic(debateRoomId);

        if (topic == null){
            topic = new ChannelTopic(String.valueOf(debateRoomId));
            redisMessageListenerContainer.addMessageListener(redisSubscriber, topic);
            TOPICS.put(debateRoomId, topic);
        }
    }

    public ChannelTopic getTopic(Long debateRoomId){
        return TOPICS.get(debateRoomId);
    }
}
