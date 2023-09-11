package com.seojs.debateking.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResposeDto {
    private final String name;
    private final int amount;
}
