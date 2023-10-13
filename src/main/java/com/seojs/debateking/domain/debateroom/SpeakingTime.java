package com.seojs.debateking.domain.debateroom;

public enum SpeakingTime {
    SEC30(30),
    SEC60(60),
    SEC120(120);

    private final int seconds;

    SpeakingTime(int seconds){
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
