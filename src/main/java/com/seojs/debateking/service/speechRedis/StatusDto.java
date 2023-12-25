package com.seojs.debateking.service.speechRedis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusDto {
    private String type;
    private Long debateRoomId;
    private boolean status;

    @Builder
    public StatusDto(String type, Long debateRoomId, boolean status) {
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.status = status;
    }
}
