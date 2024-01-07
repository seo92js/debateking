package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PositionDto {
    private String type;
    private Long debateRoomId;
    private String prosUsername;
    private String consUsername;

    @Builder
    public PositionDto(String type, Long debateRoomId, String prosUsername, String consUsername) {
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.prosUsername = prosUsername;
        this.consUsername = consUsername;
    }
}
