package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EnterDto {
    private String type;
    private Long debateRoomId;
    private String username;

    @Builder
    public EnterDto(String type, Long debateRoomId, String username){
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.username = username;
    }
}
