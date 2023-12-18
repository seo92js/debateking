package com.seojs.debateking.service.speechRedis;

import com.seojs.debateking.domain.chatRedis.ChatRedis;
import com.seojs.debateking.domain.chatRedis.ChatRedisRepository;
import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    //private final RedisTemplate redisTemplate;
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

        //sorted set
        long currentTimeMillis = System.currentTimeMillis();
        redisTemplate.opsForZSet().add("speechSortedSet:" + speechDto.getDebateRoomId(), speechRedis.getId(), currentTimeMillis);
    }

    @Transactional
    public List<SpeechResponseDto> getSpeeches(Long debateRoomId){
        //sorted set
        Set<Object> speechIds = redisTemplate.opsForZSet().range("speechSortedSet:" + debateRoomId, 0, -1);

        List<SpeechRedis> speechList = new ArrayList<>();

        for (Object speechId : speechIds) {
            String id = (String) speechId;
            SpeechRedis speech = speechRedisRepository.findById(id).orElse(null);
            if (speech != null) {
                speechList.add(speech);
            }
        }

        return speechList.stream().map(SpeechResponseDto::new).collect(Collectors.toList());

//        Set<String> speechIds = redisTemplate.opsForZSet().range("speechSortedSet:" + debateRoomId, 0, -1);
//
//        List<SpeechRedis> speechList = new ArrayList<>();
//
//        for (String speechId : speechIds) {
//            SpeechRedis speech = speechRedisRepository.findById(speechId).orElse(null);
//            if (speech != null) {
//                speechList.add(speech);
//            }
//        }
//        return speechList.stream().map(SpeechResponseDto::new).collect(Collectors.toList());

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

        //sorted set
        long currentTimeMillis = System.currentTimeMillis();
        redisTemplate.opsForZSet().add("chatSortedSet:" + chatDto.getDebateRoomId(), chatRedis.getId(), currentTimeMillis);
    }

    @Transactional
    public List<ChatResponseDto> getChats(Long debateRoomId){
        //sorted set
        Set<Object> chatIds = redisTemplate.opsForZSet().range("chatSortedSet:" + debateRoomId, 0, -1);

        List<ChatRedis> chatList = new ArrayList<>();

        for (Object chatId : chatIds) {
            String id = (String) chatId;
            ChatRedis chat = chatRedisRepository.findById(id).orElse(null);
            if (chat != null) {
                chatList.add(chat);
            }
        }

        return chatList.stream().map(ChatResponseDto::new).collect(Collectors.toList());

//        Set<String> chatIds = redisTemplate.opsForZSet().range("chatSortedSet:" + debateRoomId, 0, -1);
//
//        List<ChatRedis> chatList = new ArrayList<>();
//        for (String chatId : chatIds) {
//            ChatRedis chat = chatRedisRepository.findById(chatId).orElse(null);
//            if (chat != null) {
//                chatList.add(chat);
//            }
//        }
//        return chatList.stream().map(ChatResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void position(PositionDto positionDto){
        redisPublisher.publish(redisMessageListener.getTopic(positionDto.getDebateRoomId()), positionDto);
    }

    @Transactional
    public void enter(EnterDto enterDto){
        redisPublisher.publish(redisMessageListener.getTopic(enterDto.getDebateRoomId()), enterDto);
    }

    @Transactional
    public void exit(ExitDto exitDto){
        redisPublisher.publish(redisMessageListener.getTopic(exitDto.getDebateRoomId()), exitDto);
    }

    @Transactional
    public void ready(ReadyDto readyDto){
        redisPublisher.publish(redisMessageListener.getTopic(readyDto.getDebateRoomId()), readyDto);
    }

    @Transactional
    public void time(TimeDto timeDto){
        redisPublisher.publish(redisMessageListener.getTopic(timeDto.getDebateRoomId()), timeDto);
    }

    @Transactional
    public void speaker(SpeakerDto speakerDto) {
        redisPublisher.publish(redisMessageListener.getTopic(speakerDto.getDebateRoomId()), speakerDto);
    }
}
