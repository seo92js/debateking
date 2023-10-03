package com.seojs.debateking.web;

import com.seojs.debateking.service.speech.SpeechService;
import com.seojs.debateking.service.speechRedis.SpeechRedisService;
import com.seojs.debateking.web.dto.SpeechSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SpeechApiController {
    private final SpeechService speechService;
    private final SpeechRedisService speechRedisService;

    @PostMapping("/api/v1/speech")
    public Long save(@RequestBody SpeechSaveRequestDto speechSaveRequestDto){
        return speechService.save(speechSaveRequestDto);
    }

//    @PostMapping("/api/v2/speech")
//    public void save(@RequestBody SpeechRedisSaveRequestDto speechRedisSaveRequestDto){
//        speechRedisService.save(speechRedisSaveRequestDto);
//
//        speechRedisService.publishMessage(channelService.getChannel(speechRedisSaveRequestDto.getDebateRoomId()) , speechRedisSaveRequestDto.getSpeech());
//    }
}
