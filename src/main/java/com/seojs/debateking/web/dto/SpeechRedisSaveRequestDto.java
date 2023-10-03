package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpeechRedisSaveRequestDto {
    private Long debateRoomId;
    private String username;
    private String speech;

    @Builder
    public SpeechRedisSaveRequestDto(Long debateRoomId, String username, String speech){
        this.debateRoomId = debateRoomId;
        this.username = username;
        this.speech = speech;
    }
}
