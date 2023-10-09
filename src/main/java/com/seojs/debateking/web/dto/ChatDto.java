package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatDto {
    private Long debateRoomId;
    private String username;
    private String message;

    @Builder
    public ChatDto(Long debateRoomId, String username, String message){
        this.debateRoomId = debateRoomId;
        this.username = username;
        this.message = message;
    }
}
