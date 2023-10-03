package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateRoomSaveRequestDto {
    private Long userId;
    private String topicName;
    private String title;
    private int speakingTime;
    private int discussionTime;

    @Builder
    public DebateRoomSaveRequestDto (Long userId, String topicName, String title, int speakingTime, int discussionTime){
        this.userId = userId;
        this.topicName = topicName;
        this.title = title;
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }
}
