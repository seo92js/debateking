package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateRoomReadyUpdateRequestDto {
    private boolean prosReady;
    private boolean consReady;

    @Builder
    public DebateRoomReadyUpdateRequestDto(boolean prosReady, boolean consReady){
        this.prosReady = prosReady;
        this.consReady = consReady;
    }
}
