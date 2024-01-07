package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TimeDto {
    private String type;
    private Long debateRoomId;
    private int speakingTime;
    private int discussionTime;

    @Builder
    public TimeDto(String type, Long debateRoomId, int speakingTime, int discussionTime){
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }
}
