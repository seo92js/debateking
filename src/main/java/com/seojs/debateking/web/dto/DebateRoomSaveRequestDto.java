package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateRoomSaveRequestDto {
    private Long userId;
    private String title;
    private int speakingTime;
    private int discussionTime;

    @Builder
    public DebateRoomSaveRequestDto (Long userId, String title, int speakingTime, int discussionTime){
        this.userId = userId;
        this.title = title;
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }
}
