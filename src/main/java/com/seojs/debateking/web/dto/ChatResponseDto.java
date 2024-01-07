package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.chatRedis.ChatRedis;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatResponseDto {
    private Long debateRoomId;
    private String username;
    private String message;

    public ChatResponseDto(ChatRedis chatRedis){
        this.debateRoomId = chatRedis.getDebateRoomId();
        this.username = chatRedis.getUsername();
        this.message = chatRedis.getMessage();
    }
}
