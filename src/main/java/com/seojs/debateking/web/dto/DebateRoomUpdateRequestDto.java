package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateRoomUpdateRequestDto {
    private String topicName;
    private String title;
    private int speakingTime;
    private int discussionTime;

    @Builder
    public DebateRoomUpdateRequestDto(String topicName, String title, int speakingTime, int discussionTime){
        this.topicName = topicName;
        this.title = title;
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }
}
