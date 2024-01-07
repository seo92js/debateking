package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SpeakerDto {
    private String type;
    private Long debateRoomId;
    private String speakerName;

    @Builder
    public SpeakerDto(String type, Long debateRoomId, String speakerName){
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.speakerName = speakerName;
    }
}
