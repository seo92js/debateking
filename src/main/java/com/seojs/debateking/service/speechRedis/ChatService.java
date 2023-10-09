package com.seojs.debateking.service.speechRedis;

import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import com.seojs.debateking.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChatService {
    private final RedisPublisher redisPublisher;
    private final RedisMessageListener redisMessageListener;
    private final SpeechRedisRepository speechRedisRepository;

    @Transactional
    public void speech(ChatDto chatDto){
        redisPublisher.publish(redisMessageListener.getTopic(chatDto.getDebateRoomId()), chatDto);

        SpeechRedis speechRedis = SpeechRedis.builder()
                .debateRoomId(chatDto.getDebateRoomId())
                .username(chatDto.getUsername())
                .speech(chatDto.getMessage())
                .build();

        speechRedisRepository.save(speechRedis);
    }

    @Transactional
    public void chat(ChatDto chatDto){
        redisPublisher.publish(redisMessageListener.getTopic(chatDto.getDebateRoomId()), chatDto);
    }
}
