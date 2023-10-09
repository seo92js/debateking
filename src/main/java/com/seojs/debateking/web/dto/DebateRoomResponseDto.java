package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import lombok.Getter;

@Getter
public class DebateRoomResponseDto {
    private String category;
    private String topic;
    private String title;
    private String owner;
    private String pros;
    private boolean prosReady;
    private String cons;
    private boolean consReady;
    private int speakingTime;
    private int discussionTime;
    private int spectors;
    private boolean start;

    public DebateRoomResponseDto(DebateRoom debateRoom){
        this.category = debateRoom.getTopic().getCategory().toString();
        this.topic = debateRoom.getTopic().getName();
        this.title = debateRoom.getTitle();
        this.owner = debateRoom.getOwner().getUsername();
        if (debateRoom.getPros() != null)
            this.pros = debateRoom.getPros().getUsername();
        this.prosReady = debateRoom.isProsReady();
        if (debateRoom.getCons() != null)
            this.cons = debateRoom.getCons().getUsername();
        this.consReady = debateRoom.isConsReady();
        this.speakingTime = debateRoom.getSpeakingTime();
        this.discussionTime = debateRoom.getDiscussionTime();
        this.spectors = debateRoom.getSpectors().size();
        this.start = debateRoom.isStart();
    }
}
