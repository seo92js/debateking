package com.seojs.debateking.service.speechRedis;

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
    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonParser jsonParser;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String type = jsonParser.getType((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));

        if (type.equals("chat")) {
            ChatDto chatDto = jsonParser.toChatDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" +  chatDto.getDebateRoomId(), chatDto);
        } else if (type.equals("speech")) {
            SpeechDto speechDto = jsonParser.toSpeechDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" +  speechDto.getDebateRoomId(), speechDto);
        } else if (type.equals("position")) {
            PositionDto positionDto = jsonParser.toPositionDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" +  positionDto.getDebateRoomId(), positionDto);
        } else if (type.equals("enter")) {
            EnterDto enterDto = jsonParser.toEnterDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" +  enterDto.getDebateRoomId(), enterDto);
        } else if (type.equals("exit")) {
            ExitDto exitDto = jsonParser.toExitDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" +  exitDto.getDebateRoomId(), exitDto);
        } else if (type.equals("ready")) {
            ReadyDto readyDto = jsonParser.toReadyDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" +  readyDto.getDebateRoomId(), readyDto);
        } else if (type.equals("time")) {
            TimeDto timeDto = jsonParser.toTimeDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" + timeDto.getDebateRoomId(), timeDto);
        } else if (type.equals("speaker")) {
            SpeakerDto speakerDto = jsonParser.toSpeakerDto((String) redisTemplate.getStringSerializer().deserialize(message.getBody()));
            messagingTemplate.convertAndSend("/sub/chatting/rooms/" + speakerDto.getDebateRoomId(), speakerDto);
        }
    }
}
