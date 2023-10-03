package com.seojs.debateking.service.speechRedis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.debateking.web.dto.SpeechRedisSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonParser {

    private final ObjectMapper objectMapper;

    public String toJson(SpeechRedisSaveRequestDto speechRedisSaveRequestDto) {
        try {
            return objectMapper.writeValueAsString(speechRedisSaveRequestDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public SpeechRedisSaveRequestDto toMessageRequest(String chattingMessage) {
        try {
            return objectMapper.readValue(chattingMessage, SpeechRedisSaveRequestDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
