package com.seojs.debateking.web;

import com.seojs.debateking.service.speechRedis.SpeechRedisService;
import com.seojs.debateking.web.dto.SpeechRedisSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SpeechRedisService speechRedisService;

    @MessageMapping("/chattings/rooms/messages")
    public void send(SpeechRedisSaveRequestDto speechRedisSaveRequestDto){
        speechRedisService.send(speechRedisSaveRequestDto);
    }
}
