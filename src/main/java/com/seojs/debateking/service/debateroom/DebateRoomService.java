package com.seojs.debateking.service.debateroom;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.topic.Topic;
import com.seojs.debateking.domain.topic.TopicRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.DebateRoomResponseDto;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import com.seojs.debateking.web.dto.DebateRoomUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DebateRoomService {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final DebateRoomRepository debateRoomRepository;

    @Transactional
    public Long save(DebateRoomSaveRequestDto debateRoomSaveRequestDto){

        User user = userRepository.findById(debateRoomSaveRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + debateRoomSaveRequestDto.getUserId()));

        Topic topic = topicRepository.findByName(debateRoomSaveRequestDto.getTopicName());

        return debateRoomRepository.save(DebateRoom.builder()
                .user(user)
                .topic(topic)
                .title(debateRoomSaveRequestDto.getTitle())
                .speakingTime(debateRoomSaveRequestDto.getSpeakingTime())
                .discussionTime(debateRoomSaveRequestDto.getDiscussionTime())
                .build()).getId();
    }

    @Transactional
    public void delete(Long id){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + id));

        debateRoomRepository.delete(debateRoom);
    }

    @Transactional
    public Long update(Long id, DebateRoomUpdateRequestDto debateRoomUpdateRequestDto){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + id));

        debateRoom.update(debateRoomUpdateRequestDto.getTitle(), debateRoomUpdateRequestDto.getSpeakingTime(), debateRoomUpdateRequestDto.getDiscussionTime());

        return debateRoom.getId();
    }

    @Transactional
    public List<DebateRoomResponseDto> findAll(){

        return debateRoomRepository.findAll().stream()
                .map(DebateRoomResponseDto::new)
                .collect(Collectors.toList());
    }
}
