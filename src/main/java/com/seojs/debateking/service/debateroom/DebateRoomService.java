package com.seojs.debateking.service.debateroom;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.topic.Topic;
import com.seojs.debateking.domain.topic.TopicRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.exception.DebateRoomException;
import com.seojs.debateking.exception.UserException;
import com.seojs.debateking.web.dto.PositionDto;
import com.seojs.debateking.service.redis.RedisMessageListener;
import com.seojs.debateking.service.redis.RedisPublisher;
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
    private final RedisPublisher redisPublisher;
    private final RedisMessageListener redisMessageListener;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final DebateRoomRepository debateRoomRepository;

    @Transactional
    public Long save(DebateRoomSaveRequestDto debateRoomSaveRequestDto){

        User user = userRepository.findById(debateRoomSaveRequestDto.getUserId()).orElseThrow(() -> new UserException("유저가 없습니다. id=" + debateRoomSaveRequestDto.getUserId()));

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
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + id));

        debateRoom.getOwner().exitDebateRoom(debateRoom);

        for (User user : debateRoom.getSpectors()){
            if (!user.getId().equals(debateRoom.getOwner().getId()))
                user.exitDebateRoom(debateRoom);
        }

        debateRoomRepository.delete(debateRoom);
    }

    @Transactional
    public Long update(Long id, DebateRoomUpdateRequestDto debateRoomUpdateRequestDto){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + id));

        debateRoom.update(debateRoomUpdateRequestDto.getTitle(), debateRoomUpdateRequestDto.getSpeakingTime(), debateRoomUpdateRequestDto.getDiscussionTime());

        return debateRoom.getId();
    }

    @Transactional
    public Long updatePosition(PositionDto positionDto){
        User prosUser = null;
        User consUser = null;

        DebateRoom debateRoom = debateRoomRepository.findById(positionDto.getDebateRoomId()).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + positionDto.getDebateRoomId()));

        String pros = positionDto.getProsUsername();
        String cons = positionDto.getConsUsername();

        if (pros != null){
            prosUser = userRepository.findByUsername(positionDto.getProsUsername()).orElseThrow(() -> new UserException("유저가 없습니다=" + positionDto.getProsUsername()));
        }
        if (cons != null){
            consUser = userRepository.findByUsername(positionDto.getConsUsername()).orElseThrow(() -> new UserException("유저가 없습니다=" + positionDto.getConsUsername()));
        }

        debateRoom.updatePosition(prosUser, consUser);

        debateRoom.setConsReady(false);
        debateRoom.setProsReady(false);

        return positionDto.getDebateRoomId();
    }

    @Transactional
    public Long updateStart(Long id){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + id));

        debateRoom.setStart(true);

        return id;
    }

    @Transactional
    public Long updateStop(Long id){
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + id));

        debateRoom.setStart(false);

        return id;
    }

    @Transactional
    public DebateRoomResponseDto findById(Long id){
        return new DebateRoomResponseDto(debateRoomRepository.findById(id).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + id)));
    }

    @Transactional
    public List<DebateRoomResponseDto> findAll(){

        return debateRoomRepository.findAll().stream()
                .map(DebateRoomResponseDto::new)
                .collect(Collectors.toList());
    }
}
