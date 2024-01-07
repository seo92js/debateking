package com.seojs.debateking.service.debateroom;

import com.seojs.debateking.service.redis.RedisService;
import com.seojs.debateking.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DebateTimer {
    private final RedisService redisService;
    private final DebateRoomService debateRoomService;
    private boolean debateInProgress = false;
    private Long debateRoomId;
    private int discussionTime;
    private int speakingTime;
    private int tempSpeakingTime;
    private String currentSpeaker;
    private String prosname;
    private String consname;

    @Scheduled(fixedDelay = 1000)
    public void debateTimer() {
        if(debateInProgress) {
            discussionTime--;
            tempSpeakingTime--;

            sendTime(discussionTime, tempSpeakingTime);

            if (tempSpeakingTime <= 0) {
                tempSpeakingTime = speakingTime;

                if (currentSpeaker.equals(prosname)) {
                    currentSpeaker = consname;
                } else {
                    currentSpeaker = prosname;
                }

                speakerChange(currentSpeaker);
            }


            if (discussionTime <= 0) {
                stopTimer();

                speakerChange(null);

                readyChange();

                //투표 기능
                //메시지를 보내면, 구독 자 들에게 투표 창을 띄워야 함?
            }
        }
    }

    private void readyChange() {
        ReadyDto readyDto = ReadyDto.builder()
                .type("ready")
                .debateRoomId(debateRoomId)
                .consReady(false)
                .prosReady(false)
                .build();

        redisService.ready(readyDto);
    }

    public void startTimer(DebateDto debateDto) {
        debateRoomId = debateDto.getDebateRoomId();
        discussionTime = debateDto.getDiscussionTime();
        speakingTime = debateDto.getSpeakingTime();
        tempSpeakingTime = speakingTime;
        prosname = debateDto.getProsname();
        consname = debateDto.getConsname();
        currentSpeaker = prosname;
        debateInProgress = true;

        notify("------- 토론 시작 -------");
        speakerChange(currentSpeaker);

        debateRoomService.updateStart(debateRoomId);

        debateTimer();
    }

    public void stopTimer() {
        notify("------- 토론이 끝났습니다 -------");

        debateRoomService.updateStop(debateRoomId);

        debateInProgress = false;

        DebateDto debateDto = DebateDto.builder()
                .type("debate")
                .debateRoomId(debateRoomId)
                .status(false)
                .prosname(null)
                .consname(null)
                .discussionTime(0)
                .speakingTime(0)
                .build();

        redisService.debate(debateDto);
    }

    public void sendTime(int discussionTime, int speakingTime) {
        TimeDto timeDto = TimeDto.builder()
                .type("time")
                .debateRoomId(debateRoomId)
                .discussionTime(discussionTime)
                .speakingTime(speakingTime)
                .build();

        redisService.time(timeDto);
    }

    public void speakerChange(String currentSpeaker) {
        SpeakerDto speakerDto = SpeakerDto.builder()
                .type("speaker")
                .debateRoomId(debateRoomId)
                .speakerName(currentSpeaker)
                .build();

        redisService.speaker(speakerDto);

        notify("------- " + currentSpeaker + " 발언 -------");
    }

    public void notify(String notify) {
        SpeechDto speechDto = SpeechDto.builder()
                .type("speech")
                .debateRoomId(debateRoomId)
                .username("notify")
                .message(notify)
                .build();

        redisService.speech(speechDto);
    }
}
