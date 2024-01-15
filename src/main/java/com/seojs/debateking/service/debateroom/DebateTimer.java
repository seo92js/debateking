package com.seojs.debateking.service.debateroom;

import com.seojs.debateking.service.redis.RedisService;
import com.seojs.debateking.service.vote.VoteService;
import com.seojs.debateking.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class DebateTimer {
    private final RedisService redisService;
    private final DebateRoomService debateRoomService;
    private final VoteService voteService;
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

                //30 초 뒤에 집계함수
                aggregation();
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

    public void result(String winnerName, String loserName, boolean isDraw) {
        ResultDto resultDto = ResultDto.builder()
                .type("result")
                .debateRoomId(debateRoomId)
                .winner(winnerName)
                .loser(loserName)
                .draw(isDraw)
                .build();

        redisService.result(resultDto);
    }

    private void aggregation() {
        notify("------ 30초간 승/패 투표를 해주세요 ------");

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

        scheduler.schedule(() -> {
            try {
                int prosCount = 0, consCount = 0;
                int result = voteService.debateResult(debateRoomId);

                //0 = 찬성 승리, 1 = 반대 승리, -1 = 무승부
                if (result == 0){
                    result(prosname, consname, false);
                    notify("------- [찬성] 승리 : " + prosname + " -------");
                } else if (result == 1) {
                    result(consname, prosname, false);
                    notify("------- [반대] 승리 : " + consname + " -------");
                } else if (result == -1) {
                    result(prosname, consname, true);
                    notify("------- [무승부] -------");
                }

                voteService.deleteByDebateRoomId(debateRoomId);

            } catch (Exception e) {
            }
        }, 32, TimeUnit.SECONDS);
    }
}
