package com.seojs.debateking.domain.chatRedis;

import org.springframework.data.repository.CrudRepository;

public interface ChatRedisRepository extends CrudRepository<ChatRedis, String> , ChatRedisRepositoryCustom{
}
