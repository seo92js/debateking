package com.seojs.debateking.web;

import com.seojs.debateking.domain.chatRedis.ChatRedisRepository;
import com.seojs.debateking.domain.speechRedis.SpeechRedisRepository;
import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.service.redis.RedisMessageListener;
import com.seojs.debateking.web.dto.DebateRoomUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class DebateRoomApiController {
    private final DebateRoomService debateRoomService;
    private final SpeechRedisRepository speechRedisRepository;
    private final ChatRedisRepository chatRedisRepository;
    private final RedisMessageListener redisMessageListener;

    @DeleteMapping("/api/v1/debateroom/{id}")
    public void delete(@PathVariable Long id){

        speechRedisRepository.deleteByDebateRoomId(id);
        chatRedisRepository.deleteByDebateRoomId(id);

        debateRoomService.delete(id);

        redisMessageListener.deleteChatRoom(id);
    }

    @PutMapping("/api/v1/debateroom/{id}")
    public Long update(@PathVariable Long id, @RequestBody DebateRoomUpdateRequestDto debateRoomUpdateRequestDto){
        return debateRoomService.update(id, debateRoomUpdateRequestDto);
    }
}
