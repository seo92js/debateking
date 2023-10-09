package com.seojs.debateking.service.speechRedis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatDto {
    private String type;
    private Long debateRoomId;
    private String username;
    private String message;

    @Builder
    public ChatDto(String type, Long debateRoomId, String username, String message){
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.username = username;
        this.message = message;
    }
}
