package com.seojs.debateking.domain.speechRedis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeechRedisRepository extends CrudRepository<SpeechRedis, String> {
    List<SpeechRedis> findByDebateRoomId(Long debateRoomId);
}
