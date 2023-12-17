package com.seojs.debateking.domain.chatRedis;

public interface ChatRedisRepositoryCustom {
    void deleteByDebateRoomId(Long debateRoomId);
}
