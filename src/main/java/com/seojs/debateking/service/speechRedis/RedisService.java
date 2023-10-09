package com.seojs.debateking.service.speechRedis;

import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import com.seojs.debateking.web.dto.DebateRoomPositionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisPublisher redisPublisher;
    private final RedisMessageListener redisMessageListener;
    private final SpeechRedisRepository speechRedisRepository;

    @Transactional
    public void speech(SpeechDto speechDto){
        redisPublisher.publish(redisMessageListener.getTopic(speechDto.getDebateRoomId()), speechDto);

        SpeechRedis speechRedis = SpeechRedis.builder()
                .debateRoomId(speechDto.getDebateRoomId())
                .username(speechDto.getUsername())
                .speech(speechDto.getMessage())
                .build();

        speechRedisRepository.save(speechRedis);
    }

    @Transactional
    public void chat(ChatDto chatDto){
        redisPublisher.publish(redisMessageListener.getTopic(chatDto.getDebateRoomId()), chatDto);
    }

    @Transactional
    public void position(PositionDto positionDto){
        redisPublisher.publish(redisMessageListener.getTopic(positionDto.getDebateRoomId()), positionDto);
    }
}
