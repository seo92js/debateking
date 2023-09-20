package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.topic.Topic;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateRoomSaveRequestDto {
    private Long userId;
    private Topic topic;
    private String title;
    private int speakingTime;
    private int discussionTime;

    @Builder
    public DebateRoomSaveRequestDto (Long userId, Topic topic, String title, int speakingTime, int discussionTime){
        this.userId = userId;
        this.topic = topic;
        this.title = title;
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }
}
