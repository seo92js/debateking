package com.seojs.debateking.service.speechRedis;

import com.seojs.debateking.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisSubscriber implements MessageListener {
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisTemplate redisTemplate;
    private final JsonParser jsonParser;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        ChatDto ChatDto = jsonParser.toMessageRequest((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
        messagingTemplate.convertAndSend("/sub/chatting/rooms/" +  ChatDto.getDebateRoomId(), ChatDto);
    }
}
