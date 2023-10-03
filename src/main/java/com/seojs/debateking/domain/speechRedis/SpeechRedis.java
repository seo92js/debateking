package com.seojs.debateking.domain.speechRedis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@RedisHash(value = "speechRedis")
public class SpeechRedis {
    @Id
    private String id;
    private Long debateRoomId;
    private String username;
    private String speech;

    @Builder
    public SpeechRedis(Long debateRoomId, String username, String speech){
        this.debateRoomId = debateRoomId;
        this.username = username;
        this.speech = speech;
    }
}
