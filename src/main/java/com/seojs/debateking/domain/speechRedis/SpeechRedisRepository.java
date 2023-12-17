package com.seojs.debateking.domain.speechRedis;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpeechRedisRepository extends CrudRepository<SpeechRedis, String>, SpeechRedisRepositoryCustom {
}
