package com.seojs.debateking.service.speechRedis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.debateking.web.dto.ChatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonParser {

    private final ObjectMapper objectMapper;

    public String toJson(ChatDto chatDto) {
        try {
            return objectMapper.writeValueAsString(chatDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatDto toMessageRequest(String chattingMessage) {
        try {
            return objectMapper.readValue(chattingMessage, ChatDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
