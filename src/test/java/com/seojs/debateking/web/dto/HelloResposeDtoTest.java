package com.seojs.debateking.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloResposeDtoTest {
    @Test
    public void 롬복_테스트(){
        //given
        String username = "test";
        int amount = 1000;
        //when
        HelloResposeDto dto = new HelloResposeDto(username, amount);
        //then
        Assertions.assertThat(dto.getUsername()).isEqualTo(username);
        Assertions.assertThat(dto.getAmount()).isEqualTo(amount);
    }
}