package com.seojs.debateking.service.speechRedis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExitDto {
    private String type;
    private Long debateRoomId;
    private String username;

    @Builder
    public ExitDto(String type, Long debateRoomId, String username){
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.username = username;
    }
}
