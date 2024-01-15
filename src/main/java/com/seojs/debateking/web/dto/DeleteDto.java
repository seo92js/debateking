package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DeleteDto {
    private String type;
    private Long debateRoomId;

    @Builder
    public DeleteDto(String type, Long debateRoomId) {
        this.type = type;
        this.debateRoomId = debateRoomId;
    }
}
