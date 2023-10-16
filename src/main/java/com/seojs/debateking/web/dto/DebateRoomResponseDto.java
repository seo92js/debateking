package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DebateRoomResponseDto {
    private Long id;
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
    private int spectorsNum;
    private List<String> spectorsName = new ArrayList<>();
    private boolean start;

    public DebateRoomResponseDto(DebateRoom debateRoom){
        this.id = debateRoom.getId();
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
        this.spectorsNum = debateRoom.getSpectors().size();
        for (int i = 0; i < spectorsNum; i++){
            this.spectorsName.add(debateRoom.getSpectors().get(i).getUsername());
        }
        this.start = debateRoom.isStart();
    }
}
