package com.seojs.debateking.service.speechRedis;

import com.seojs.debateking.domain.chatRedis.ChatRedis;
import com.seojs.debateking.domain.chatRedis.ChatRedisRepository;
import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisPublisher redisPublisher;
    private final RedisMessageListener redisMessageListener;
    private final ChatRedisRepository chatRedisRepository;
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
    public List<SpeechResponseDto> getSpeeches(Long debateRoomId){
        return speechRedisRepository.findByDebateRoomId(debateRoomId).stream().map(SpeechResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void chat(ChatDto chatDto){
        redisPublisher.publish(redisMessageListener.getTopic(chatDto.getDebateRoomId()), chatDto);

        ChatRedis chatRedis = ChatRedis.builder()
                .debateRoomId(chatDto.getDebateRoomId())
                .username(chatDto.getUsername())
                .message(chatDto.getMessage())
                .build();

        chatRedisRepository.save(chatRedis);
    }

    @Transactional
    public List<ChatResponseDto> getChats(Long debateRoomId){
        return chatRedisRepository.findByDebateRoomId(debateRoomId).stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void position(PositionDto positionDto){
        redisPublisher.publish(redisMessageListener.getTopic(positionDto.getDebateRoomId()), positionDto);
    }
}
