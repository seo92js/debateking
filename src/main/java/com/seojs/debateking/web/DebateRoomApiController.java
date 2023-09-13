package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DebateRoomApiController {
    private final DebateRoomService debateRoomService;

    @PostMapping("/api/v1/debateroom")
    public Long save(@RequestBody DebateRoomSaveRequestDto debateRoomSaveRequestDto){
        return debateRoomService.save(debateRoomSaveRequestDto);
    }
}
