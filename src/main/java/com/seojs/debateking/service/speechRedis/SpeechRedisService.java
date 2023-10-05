package com.seojs.debateking.service.speechRedis;

import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import com.seojs.debateking.web.dto.SpeechRedisSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SpeechRedisService {
    private final RedisPublisher redisPublisher;
    private final RedisMessageListener redisMessageListener;
    private final SpeechRedisRepository speechRedisRepository;


//    private final SpeechRedisRepository speechRedisRepository;
//    private final RedisTemplate<String, String> redisTemplate;
//
//    @Transactional
//    public void save(SpeechRedisSaveRequestDto speechRedisSaveRequestDto){
//        SpeechRedis speechRedis = SpeechRedis.builder()
//                .debateRoomId(speechRedisSaveRequestDto.getDebateRoomId())
//                .username(speechRedisSaveRequestDto.getUsername())
//                .speech(speechRedisSaveRequestDto.getSpeech())
//                .build();
//
//        speechRedisRepository.save(speechRedis);
//    }
//
//    @Transactional
//    public void publishMessage(ChannelTopic topic, String message){
//        redisTemplate.convertAndSend(topic.getTopic(), message);
//    }

    @Transactional
    public void send(SpeechRedisSaveRequestDto speechRedisSaveRequestDto){
        redisPublisher.publish(redisMessageListener.getTopic(speechRedisSaveRequestDto.getDebateRoomId()), speechRedisSaveRequestDto);

        SpeechRedis speechRedis = SpeechRedis.builder()
                .debateRoomId(speechRedisSaveRequestDto.getDebateRoomId())
                .username(speechRedisSaveRequestDto.getUsername())
                .speech(speechRedisSaveRequestDto.getSpeech())
                .build();

        speechRedisRepository.save(speechRedis);
    }
}
