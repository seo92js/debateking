package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import lombok.Getter;

@Getter
public class DebateRoomResponseDto {
    private String title;
    private String owner;
    private int speakingTime;
    private int discussionTime;
    private int spectors;

    public DebateRoomResponseDto(DebateRoom debateRoom){
        this.title = debateRoom.getTitle();
        this.owner = debateRoom.getOwner().getUsername();
        this.speakingTime = debateRoom.getSpeakingTime();
        this.discussionTime = debateRoom.getDiscussionTime();
        this.spectors = debateRoom.getSpectors().size();
    }
}
