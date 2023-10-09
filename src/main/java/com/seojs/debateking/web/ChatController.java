package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.service.speechRedis.RedisService;
import com.seojs.debateking.service.speechRedis.ChatDto;
import com.seojs.debateking.service.speechRedis.SpeechDto;
import com.seojs.debateking.web.dto.DebateRoomPositionUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
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
    public void updatePosition(DebateRoomPositionUpdateRequestDto debateRoomPositionUpdateRequestDto){
        debateRoomService.updatePosition(debateRoomPositionUpdateRequestDto);
    }
}
