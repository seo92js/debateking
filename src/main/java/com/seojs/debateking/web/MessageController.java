package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.service.speechRedis.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final RedisService redisService;
    private final DebateRoomService debateRoomService;

    @MessageMapping("/chattings/rooms/speech")
    public void speech(SpeechDto speechDto){
        redisService.speech(speechDto);
    }

    @MessageMapping("/chattings/rooms/chat")
    public void chat(ChatDto chatDto){
        redisService.chat(chatDto);
    }

    @MessageMapping("/chattings/rooms/position")
    public void updatePosition(PositionDto positionDto){

        redisService.position(positionDto);

        debateRoomService.updatePosition(positionDto);
    }

    @MessageMapping("/chattings/rooms/enter")
    public void enter(EnterDto enterDto){
        redisService.enter(enterDto);
    }

    @MessageMapping("/chattings/rooms/exit")
    public void exit(ExitDto exitDto){
        redisService.exit(exitDto);
    }

    @MessageMapping("/chattings/rooms/ready")
    public void ready(ReadyDto readyDto){
        redisService.ready(readyDto);
    }

    @MessageMapping("/chattings/rooms/time")
    public void ready(TimeDto timeDto){
        redisService.time(timeDto);
    }

    @MessageMapping("/chattings/rooms/speaker")
    public void speaker(SpeakerDto speakerDto) {
        redisService.speaker(speakerDto);
    }
}
