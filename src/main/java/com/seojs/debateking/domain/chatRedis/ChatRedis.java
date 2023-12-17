package com.seojs.debateking.domain.chatRedis;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "chatRedis", timeToLive = 1800)
public class ChatRedis {
    @Id
    private String id;
    @Indexed
    private Long debateRoomId;
    private String username;
    private String message;

    @Builder
    public ChatRedis(Long debateRoomId, String username, String message){
        this.debateRoomId = debateRoomId;
        this.username = username;
        this.message = message;
    }
}
