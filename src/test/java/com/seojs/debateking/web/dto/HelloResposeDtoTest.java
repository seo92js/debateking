package com.seojs.debateking.web.dto;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class HelloResposeDtoTest {
    @Test
    public void 롬복_테스트(){
        //given
        String name = "test";
        int amount = 1000;
        //when
        HelloResposeDto dto = new HelloResposeDto(name, amount);
        //then
        Assertions.assertThat(dto.getName()).isEqualTo(name);
        Assertions.assertThat(dto.getAmount()).isEqualTo(amount);
    }
}