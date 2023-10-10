package com.seojs.debateking.domain.chatRedis;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ChatRedisRepository extends CrudRepository<ChatRedis, String> {
    List<ChatRedis> findByDebateRoomId(Long debateRoomId);
}
