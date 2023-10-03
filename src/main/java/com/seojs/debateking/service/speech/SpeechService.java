package com.seojs.debateking.service.speech;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.speech.Speech;
import com.seojs.debateking.domain.speech.SpeechRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.SpeechSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SpeechService {
    private final UserRepository userRepository;
    private final DebateRoomRepository debateRoomRepository;
    private final SpeechRepository speechRepository;

    @Transactional
    public Long save(SpeechSaveRequestDto speechSaveRequestDto){
        DebateRoom debateRoom = debateRoomRepository.findById(speechSaveRequestDto.getDebateRoomId()).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + speechSaveRequestDto.getDebateRoomId()));

        User user = userRepository.findById(speechSaveRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + speechSaveRequestDto.getUserId()));

        Speech speech = Speech.builder()
                .debateRoom(debateRoom)
                .user(user)
                .speech(speechSaveRequestDto.getSpeech())
                .build();

        return speechRepository.save(speech).getId();
    }
}
