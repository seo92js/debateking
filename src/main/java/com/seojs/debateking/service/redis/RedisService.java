package com.seojs.debateking.service.redis;

import com.seojs.debateking.domain.chatRedis.ChatRedis;
import com.seojs.debateking.domain.chatRedis.ChatRedisRepository;
import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.*;
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
    private final RedisPublisher redisPublisher;
    private final RedisMessageListener redisMessageListener;
    private final ChatRedisRepository chatRedisRepository;
    private final SpeechRedisRepository speechRedisRepository;
    private final UserRepository userRepository;
    private final DebateRoomRepository debateRoomRepository;

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

        DebateRoom debateRoom = debateRoomRepository.findById(readyDto.getDebateRoomId()).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + readyDto.getDebateRoomId()));

        debateRoom.setConsReady(readyDto.isConsReady());
        debateRoom.setProsReady(readyDto.isProsReady());
    }

    @Transactional
    public void time(TimeDto timeDto){
        redisPublisher.publish(redisMessageListener.getTopic(timeDto.getDebateRoomId()), timeDto);
    }

    @Transactional
    public void speaker(SpeakerDto speakerDto) {
        redisPublisher.publish(redisMessageListener.getTopic(speakerDto.getDebateRoomId()), speakerDto);

        DebateRoom debateRoom = debateRoomRepository.findById(speakerDto.getDebateRoomId()).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + speakerDto.getDebateRoomId()));

        if (speakerDto.getSpeakerName() == null) {
            debateRoom.updateSpeaker(null);
        }
        else {
            User speaker = userRepository.findByUsername(speakerDto.getSpeakerName()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다=" + speakerDto.getSpeakerName()));
            debateRoom.updateSpeaker(speaker);
        }
    }

    @Transactional
    public void result(ResultDto resultDto) {

        User winner = userRepository.findByUsername(resultDto.getWinner()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. username=" + resultDto.getWinner()));
        User loser = userRepository.findByUsername(resultDto.getLoser()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. username=" + resultDto.getLoser()));

        if (resultDto.isDraw()) {
            winner.draw();
            loser.draw();
        } else {
            winner.win();
            loser.lose();
        }

        redisPublisher.publish(redisMessageListener.getTopic(resultDto.getDebateRoomId()), resultDto);
    }

    @Transactional
    public void debate(DebateDto debateDto) {
        redisPublisher.publish(redisMessageListener.getTopic(debateDto.getDebateRoomId()), debateDto);
    }
}
