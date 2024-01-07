package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReadyDto {
    private String type;
    private Long debateRoomId;
    private boolean prosReady;
    private boolean consReady;

    @Builder
    public ReadyDto(String type, Long debateRoomId, boolean prosReady, boolean consReady){
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.prosReady = prosReady;
        this.consReady = consReady;
    }
}
