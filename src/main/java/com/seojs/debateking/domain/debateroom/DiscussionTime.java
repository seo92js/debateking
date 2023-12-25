package com.seojs.debateking.domain.debateroom;

public enum DiscussionTime {
    SEC120(120),
    SEC600(600),
    SEC900(900),
    SEC1200(1200),
    SEC1500(1500),
    SEC1800(1800);

    private final int seconds;

    DiscussionTime(int seconds){
        this.seconds = seconds;
    }

    public int getSeconds(){
        return seconds;
    }
}
