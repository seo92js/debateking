package com.seojs.debateking.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class HelloResposeDto {
    private final String username;
    private final int amount;
}
