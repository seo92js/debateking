package com.seojs.debateking.service.debateroom;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class DebateRoomService {
    private final UserRepository userRepository;
    private final DebateRoomRepository debateRoomRepository;

    @Transactional
    public Long save(DebateRoomSaveRequestDto debateRoomSaveRequestDto){

        User user = userRepository.findById(debateRoomSaveRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + debateRoomSaveRequestDto.getUserId()));

        return debateRoomRepository.save(DebateRoom.builder()
                .user(user)
                .title(debateRoomSaveRequestDto.getTitle())
                .speakingTime(debateRoomSaveRequestDto.getSpeakingTime())
                .discussionTime(debateRoomSaveRequestDto.getDiscussionTime())
                .build()).getId();
    }
}
