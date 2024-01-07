package com.seojs.debateking.service.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seojs.debateking.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class JsonParser {

    private final ObjectMapper objectMapper;

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

    public PositionDto toPositionDto(String chattingMessage){
        try {
            return objectMapper.readValue(chattingMessage, PositionDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public EnterDto toEnterDto(String chattingMessage){
        try {
            return objectMapper.readValue(chattingMessage, EnterDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ExitDto toExitDto(String chattingMessage){
        try {
            return objectMapper.readValue(chattingMessage, ExitDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ReadyDto toReadyDto(String chattingMessage){
        try {
            return objectMapper.readValue(chattingMessage, ReadyDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TimeDto toTimeDto(String chattingMessage){
        try {
            return objectMapper.readValue(chattingMessage, TimeDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public SpeakerDto toSpeakerDto(String chattingMessage){
        try {
            return objectMapper.readValue(chattingMessage, SpeakerDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultDto toResultDto(String chattingMessage) {
        try {
            return objectMapper.readValue(chattingMessage, ResultDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public DebateDto toDebateDto(String chattingMessage) {
        try {
            return objectMapper.readValue(chattingMessage, DebateDto.class);
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
