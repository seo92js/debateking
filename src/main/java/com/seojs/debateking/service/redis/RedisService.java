package com.seojs.debateking.service.redis;

import com.seojs.debateking.domain.chatRedis.ChatRedis;
import com.seojs.debateking.domain.chatRedis.ChatRedisRepository;
import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.exception.DebateRoomException;
import com.seojs.debateking.exception.UserException;
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
    private final RedisMessageListener redisMessageListener;
    private final ChatRedisRepository chatRedisRepository;
    private final SpeechRedisRepository speechRedisRepository;
    private final UserRepository userRepository;
    private final DebateRoomRepository debateRoomRepository;

    @Transactional
    public void speech(SpeechDto speechDto){
        redisTemplate.convertAndSend(redisMessageListener.getTopic(speechDto.getDebateRoomId()).getTopic(), speechDto);

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
        redisTemplate.convertAndSend(redisMessageListener.getTopic(chatDto.getDebateRoomId()).getTopic(), chatDto);

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
        redisTemplate.convertAndSend(redisMessageListener.getTopic(positionDto.getDebateRoomId()).getTopic(), positionDto);
    }

    @Transactional
    public void enter(EnterDto enterDto){
        redisTemplate.convertAndSend(redisMessageListener.getTopic(enterDto.getDebateRoomId()).getTopic(), enterDto);

        User user = userRepository.findByUsername(enterDto.getUsername()).orElseThrow(() -> new UserException("유저가 없습니다. username=" + enterDto.getUsername()));

        DebateRoom debateRoom = debateRoomRepository.findById(enterDto.getDebateRoomId()).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id" + enterDto.getDebateRoomId()));

        user.enterDebateRoom(debateRoom);
    }

    @Transactional
    public void exit(ExitDto exitDto){
        redisTemplate.convertAndSend(redisMessageListener.getTopic(exitDto.getDebateRoomId()).getTopic(), exitDto);
    }

    @Transactional
    public void ready(ReadyDto readyDto){
        redisTemplate.convertAndSend(redisMessageListener.getTopic(readyDto.getDebateRoomId()).getTopic(), readyDto);

        DebateRoom debateRoom = debateRoomRepository.findById(readyDto.getDebateRoomId()).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + readyDto.getDebateRoomId()));

        debateRoom.setConsReady(readyDto.isConsReady());
        debateRoom.setProsReady(readyDto.isProsReady());
    }

    @Transactional
    public void time(TimeDto timeDto){
        redisTemplate.convertAndSend(redisMessageListener.getTopic(timeDto.getDebateRoomId()).getTopic(), timeDto);
    }

    @Transactional
    public void speaker(SpeakerDto speakerDto) {
        redisTemplate.convertAndSend(redisMessageListener.getTopic(speakerDto.getDebateRoomId()).getTopic(), speakerDto);

        DebateRoom debateRoom = debateRoomRepository.findById(speakerDto.getDebateRoomId()).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + speakerDto.getDebateRoomId()));

        if (speakerDto.getSpeakerName() == null) {
            debateRoom.updateSpeaker(null);
        }
        else {
            User speaker = userRepository.findByUsername(speakerDto.getSpeakerName()).orElseThrow(() -> new UserException("유저가 없습니다=" + speakerDto.getSpeakerName()));
            debateRoom.updateSpeaker(speaker);
        }
    }

    @Transactional
    public void result(ResultDto resultDto) {

        User winner = userRepository.findByUsername(resultDto.getWinner()).orElseThrow(() -> new UserException("유저가 없습니다. username=" + resultDto.getWinner()));
        User loser = userRepository.findByUsername(resultDto.getLoser()).orElseThrow(() -> new UserException("유저가 없습니다. username=" + resultDto.getLoser()));

        if (resultDto.isDraw()) {
            winner.draw();
            loser.draw();
        } else {
            winner.win();
            loser.lose();
        }

        redisTemplate.convertAndSend(redisMessageListener.getTopic(resultDto.getDebateRoomId()).getTopic(), resultDto);
    }

    @Transactional
    public void debate(DebateDto debateDto) {
        redisTemplate.convertAndSend(redisMessageListener.getTopic(debateDto.getDebateRoomId()).getTopic(), debateDto);
    }

    @Transactional
    public void delete(DeleteDto deleteDto) {
        redisTemplate.convertAndSend(redisMessageListener.getTopic(deleteDto.getDebateRoomId()).getTopic(), deleteDto);
    }
}
