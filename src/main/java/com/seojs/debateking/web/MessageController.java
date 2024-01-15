package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.service.debateroom.DebateTimer;
import com.seojs.debateking.service.redis.*;
import com.seojs.debateking.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {
    private final RedisService redisService;
    private final DebateRoomService debateRoomService;
    private final DebateTimer debateTimer;

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

    @MessageMapping("/chattings/rooms/result")
    public void result(ResultDto resultDto) {
        redisService.result(resultDto);
    }

    @MessageMapping("/chattings/rooms/debate")
    public void debate(DebateDto debateDto) {
        if (debateDto.isStatus())
            debateTimer.startTimer(debateDto);
        else
            debateTimer.stopTimer();

        redisService.debate(debateDto);
    }

    @MessageMapping("/chattings/rooms/delete")
    public void delete(DeleteDto deleteDto) {
        redisService.delete(deleteDto);
    }
}
