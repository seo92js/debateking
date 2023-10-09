package com.seojs.debateking.service.speechRedis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonParser {

    private final ObjectMapper objectMapper;

    public String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ChatDto toChatDto(String chattingMessage) {
        try {
            return objectMapper.readValue(chattingMessage, ChatDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public SpeechDto toSpeechDto(String chattingMessage){
        try {
            return objectMapper.readValue(chattingMessage, SpeechDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String getType(String chattingMessage){
        try {
            JsonNode jsonNode = objectMapper.readTree(chattingMessage);

            return jsonNode.get("type").asText();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
