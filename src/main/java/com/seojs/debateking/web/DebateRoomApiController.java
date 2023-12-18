package com.seojs.debateking.web;

import com.seojs.debateking.domain.chatRedis.ChatRedisRepository;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.web.dto.DebateRoomReadyUpdateRequestDto;
import com.seojs.debateking.web.dto.DebateRoomUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DebateRoomApiController {
    private final DebateRoomService debateRoomService;
    private final SpeechRedisRepository speechRedisRepository;
    private final ChatRedisRepository chatRedisRepository;

    @DeleteMapping("/api/v1/debateroom/{id}")
    public void delete(@PathVariable Long id){

        speechRedisRepository.deleteByDebateRoomId(id);
        chatRedisRepository.deleteByDebateRoomId(id);

        debateRoomService.delete(id);
    }

    @PutMapping("/api/v1/debateroom/{id}")
    public Long update(@PathVariable Long id, @RequestBody DebateRoomUpdateRequestDto debateRoomUpdateRequestDto){
        return debateRoomService.update(id, debateRoomUpdateRequestDto);
    }

    @PutMapping("/api/v1/debateroom/{id}/ready")
    public Long updateReady(@PathVariable Long id, @RequestBody DebateRoomReadyUpdateRequestDto debateRoomReadyUpdateRequestDto){
        return debateRoomService.updateReady(id, debateRoomReadyUpdateRequestDto);
    }

    @PutMapping("/api/v1/debateroom/{id}/start")
    public Long updateStart(@PathVariable Long id){
        return debateRoomService.updateStart(id);
    }

    @PutMapping("/api/v1/debateroom/{id}/stop")
    public Long updateStop(@PathVariable Long id){
        return debateRoomService.updateStop(id);
    }
}
