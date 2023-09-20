package com.seojs.debateking.domain.topic;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    POLITICS("정치"),
    RELIGION("종교"),
    SOCIETY("사회"),
    ECONOMY("경제"),
    ENTERTAINMENTS("연예"),
    SPORTS("스포츠"),
    SCIENCE("과학"),
    HEALTH("건강"),
    ENVIRONMENT("환경"),
    EDUCATION("교육"),
    HISTORY("역사"),
    CULTURE("문화"),
    MEDICINE("의학"),
    TECHNOLOGY("기술"),
    BUSINESS("비즈니스");

    private final String category;
}
