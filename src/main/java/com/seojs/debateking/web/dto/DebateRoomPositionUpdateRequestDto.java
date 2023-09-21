package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateRoomPositionUpdateRequestDto {
    private String prosUsername;
    private String consUsername;

    @Builder
    public DebateRoomPositionUpdateRequestDto(String prosUsername, String consUsername){
        this.prosUsername = prosUsername;
        this.consUsername = consUsername;
    }
}
