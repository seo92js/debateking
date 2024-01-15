package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class VoteDto {
    private Long debateRoomId;
    private boolean prosCons;

    @Builder
    public VoteDto(Long debateRoomId, boolean prosCons) {
        this.debateRoomId = debateRoomId;
        this.prosCons = prosCons;
    }
}
