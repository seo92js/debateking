package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateDto {
    private String type;
    private Long debateRoomId;
    private boolean status;
    private String prosname;
    private String consname;
    private int discussionTime;
    private int speakingTime;

    @Builder
    public DebateDto(String type, Long debateRoomId, boolean status, String prosname, String consname, int discussionTime, int speakingTime) {
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.status = status;
        this.prosname = prosname;
        this.consname = consname;
        this.discussionTime = discussionTime;
        this.speakingTime = speakingTime;
    }
}
