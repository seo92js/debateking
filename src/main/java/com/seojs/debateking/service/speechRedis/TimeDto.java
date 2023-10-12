package com.seojs.debateking.service.speechRedis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TimeDto {
    private String type;
    private Long debateRoomId;
    private int time;

    @Builder
    public TimeDto(String type, Long debateRoomId, int time){
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.time = time;
    }
}
