package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.service.speechRedis.RedisMessageListener;
import com.seojs.debateking.web.dto.DebateRoomPositionUpdateRequestDto;
import com.seojs.debateking.web.dto.DebateRoomReadyUpdateRequestDto;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import com.seojs.debateking.web.dto.DebateRoomUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DebateRoomApiController {
    private final RedisMessageListenerContainer redisMessageListenerContainer;
    private final RedisMessageListener redisMessageListener;
    private final DebateRoomService debateRoomService;

    @PostMapping("/api/v1/debateroom")
    public Long save(@RequestBody DebateRoomSaveRequestDto debateRoomSaveRequestDto){
        Long id = debateRoomService.save(debateRoomSaveRequestDto);

        redisMessageListener.enterChatRoom(id);

        return id;
    }

    @DeleteMapping("/api/v1/debateroom/{id}")
    public void delete(@PathVariable Long id){
        debateRoomService.delete(id);
    }

    @PutMapping("/api/v1/debateroom/{id}")
    public Long update(@PathVariable Long id, @RequestBody DebateRoomUpdateRequestDto debateRoomUpdateRequestDto){
        return debateRoomService.update(id, debateRoomUpdateRequestDto);
    }

//    @PutMapping("/api/v1/debateroom/{id}/position")
//    public Long updatePosition(@PathVariable Long id, @RequestBody DebateRoomPositionUpdateRequestDto debateRoomPositionUpdateRequestDto){
//        return debateRoomService.updatePosition(id, debateRoomPositionUpdateRequestDto);
//    }

    @PutMapping("/api/v1/debateroom/{id}/ready")
    public Long updateReady(@PathVariable Long id, @RequestBody DebateRoomReadyUpdateRequestDto debateRoomReadyUpdateRequestDto){
        return debateRoomService.updateReady(id, debateRoomReadyUpdateRequestDto);
    }

    @PutMapping("/api/v1/debateroom/{id}/start")
    public Long updateStart(@PathVariable Long id){
        return debateRoomService.updateStart(id);
    }
}
