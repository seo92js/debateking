package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DebateRoomPositionUpdateRequestDto {
    private Long debateRoomId;
    private String prosUsername;
    private String consUsername;

    @Builder
    public DebateRoomPositionUpdateRequestDto(Long debateRoomId, String prosUsername, String consUsername){
        this.debateRoomId = debateRoomId;
        this.prosUsername = prosUsername;
        this.consUsername = consUsername;
    }
}
