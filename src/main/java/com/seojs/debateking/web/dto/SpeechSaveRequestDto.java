package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SpeechSaveRequestDto {
    private Long debateRoomId;
    private Long userId;
    private String speech;

    @Builder
    public SpeechSaveRequestDto(Long debateRoomId, Long userId, String speech){
        this.debateRoomId = debateRoomId;
        this.userId = userId;
        this.speech = speech;
    }
}
