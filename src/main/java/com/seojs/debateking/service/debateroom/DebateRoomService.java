package com.seojs.debateking.service.debateroom;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.topic.Topic;
import com.seojs.debateking.domain.topic.TopicRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.service.speechRedis.RedisMessageListener;
import com.seojs.debateking.service.speechRedis.RedisPublisher;
import com.seojs.debateking.web.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DebateRoomService {
    private final RedisPublisher redisPublisher;
    private final RedisMessageListener redisMessageListener;
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

        debateRoom.getOwner().exitDebateRoom(debateRoom);

        for (User user : debateRoom.getSpectors()){
            user.exitDebateRoom(debateRoom);
        }

        debateRoomRepository.delete(debateRoom);
    }

    @Transactional
    public Long update(Long id, DebateRoomUpdateRequestDto debateRoomUpdateRequestDto){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + id));

        debateRoom.update(debateRoomUpdateRequestDto.getTitle(), debateRoomUpdateRequestDto.getSpeakingTime(), debateRoomUpdateRequestDto.getDiscussionTime());

        return debateRoom.getId();
    }

    @Transactional
    public Long updatePosition(DebateRoomPositionUpdateRequestDto debateRoomPositionUpdateRequestDto){
        User prosUser = null;
        User consUser = null;

        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomPositionUpdateRequestDto.getDebateRoomId()).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + debateRoomPositionUpdateRequestDto.getDebateRoomId()));

        String pros = debateRoomPositionUpdateRequestDto.getProsUsername();
        String cons = debateRoomPositionUpdateRequestDto.getConsUsername();

        if (pros != null){
            prosUser = userRepository.findByUsername(debateRoomPositionUpdateRequestDto.getProsUsername()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다=" + debateRoomPositionUpdateRequestDto.getProsUsername()));
        }
        if (cons != null){
            consUser = userRepository.findByUsername(debateRoomPositionUpdateRequestDto.getConsUsername()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다=" + debateRoomPositionUpdateRequestDto.getConsUsername()));
        }

        debateRoom.updatePosition(prosUser, consUser);

        debateRoom.setConsReady(false);
        debateRoom.setProsReady(false);

        redisPublisher.publish(redisMessageListener.getTopic(debateRoomPositionUpdateRequestDto.getDebateRoomId()), debateRoomPositionUpdateRequestDto);

        return debateRoomPositionUpdateRequestDto.getDebateRoomId();
    }

    @Transactional
    public Long updateReady(Long id, DebateRoomReadyUpdateRequestDto debateRoomReadyUpdateRequestDto){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + id));

        debateRoom.setProsReady(debateRoomReadyUpdateRequestDto.isProsReady());
        debateRoom.setConsReady(debateRoomReadyUpdateRequestDto.isConsReady());

        return id;
    }

    @Transactional
    public Long updateStart(Long id){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + id));

        debateRoom.setStart(true);

        return id;
    }

    @Transactional
    public DebateRoomResponseDto findById(Long id){
        return new DebateRoomResponseDto(debateRoomRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("토론방이 없습니다. id=" + id)));
    }

    @Transactional
    public List<DebateRoomResponseDto> findAll(){

        return debateRoomRepository.findAll().stream()
                .map(DebateRoomResponseDto::new)
                .collect(Collectors.toList());
    }
}
