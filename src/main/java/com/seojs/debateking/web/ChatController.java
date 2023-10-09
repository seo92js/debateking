package com.seojs.debateking.web;

import com.seojs.debateking.service.speechRedis.ChatService;
import com.seojs.debateking.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final ChatService chatService;

    @MessageMapping("/chattings/rooms/speech")
    public void speech(ChatDto ChatDto){
        chatService.speech(ChatDto);
    }

    @MessageMapping("/chattings/rooms/chat")
    public void chat(ChatDto chatDto){
        chatService.chat(chatDto);
    }
}
