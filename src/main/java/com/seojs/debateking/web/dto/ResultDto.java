package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ResultDto {
    private String type;
    private Long debateRoomId;
    private String winner;
    private String loser;
    private boolean draw;

    @Builder
    public ResultDto(String type, Long debateRoomId, String winner, String loser, boolean draw) {
        this.type = type;
        this.debateRoomId = debateRoomId;
        this.winner = winner;
        this.loser = loser;
        this.draw = draw;
    }
}
