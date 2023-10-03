package com.seojs.debateking.domain.speechRedis;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpeechRedisRepositoryTest {

    @Autowired
    private SpeechRedisRepository speechRedisRepository;

    @BeforeEach
    public void clean(){
        speechRedisRepository.deleteAll();
    }

    @Test
    public void save(){
        //given
        SpeechRedis speechRedis = new SpeechRedis(1L,"user", "speech");
        speechRedisRepository.save(speechRedis);
        //when
        //then
        assertThat(speechRedisRepository.count()).isEqualTo(1);
    }
}