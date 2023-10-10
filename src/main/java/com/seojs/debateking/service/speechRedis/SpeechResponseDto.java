package com.seojs.debateking.service.speechRedis;

import com.seojs.debateking.domain.speechRedis.SpeechRedis;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpeechResponseDto {
    private Long debateRoomId;
    private String username;
    private String speech;

    public SpeechResponseDto(SpeechRedis speechRedis){
        this.debateRoomId = speechRedis.getDebateRoomId();
        this.username = speechRedis.getUsername();
        this.speech = speechRedis.getSpeech();
    }
}
