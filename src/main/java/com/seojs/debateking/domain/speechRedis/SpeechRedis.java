package com.seojs.debateking.domain.speechRedis;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor
@RedisHash(value = "speechRedis", timeToLive = 1800)
public class SpeechRedis {
    @Id
    private String id;
    @Indexed
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
