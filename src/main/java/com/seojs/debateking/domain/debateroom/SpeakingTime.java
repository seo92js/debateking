package com.seojs.debateking.domain.debateroom;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SpeakingTime {
    SEC30(30),
    SEC60(60),
    SEC120(120);

    private final int seconds;
}
