package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import com.seojs.debateking.web.dto.DebateRoomUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DebateRoomApiController {
    private final DebateRoomService debateRoomService;

    @PostMapping("/api/v1/debateroom")
    public Long save(@RequestBody DebateRoomSaveRequestDto debateRoomSaveRequestDto){
        return debateRoomService.save(debateRoomSaveRequestDto);
    }

    @DeleteMapping("/api/v1/debateroom/{id}")
    public void delete(@PathVariable Long id){
        debateRoomService.delete(id);
    }

    @PutMapping("/api/v1/debateroom/{id}")
    public Long update(@PathVariable Long id, @RequestBody DebateRoomUpdateRequestDto debateRoomUpdateRequestDto){
        return debateRoomService.update(id, debateRoomUpdateRequestDto);
    }
}
