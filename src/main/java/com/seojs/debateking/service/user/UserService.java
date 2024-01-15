package com.seojs.debateking.service.user;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.exception.DebateRoomException;
import com.seojs.debateking.exception.UserException;
import com.seojs.debateking.web.dto.UserResponseDto;
import com.seojs.debateking.web.dto.UserSaveRequestDto;
import com.seojs.debateking.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final DebateRoomRepository debateRoomRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserSaveRequestDto userSaveRequestDto){

        User user = User.builder()
                .username(userSaveRequestDto.getUsername())
                .password(passwordEncoder.encode(userSaveRequestDto.getPassword()))
                .build();

        return userRepository.save(user).getId();
    }

    @Transactional
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("유저가 없습니다. id=" + id));

        return new UserResponseDto(user);
    }

    @Transactional
    public Long update(Long id, UserUpdateRequestDto userUpdateRequestDto){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("유저가 없습니다. id=" + id));

        user.update(userUpdateRequestDto.getUsername(), userUpdateRequestDto.getPassword());

        return id;
    }

    @Transactional
    public Long enterDebateRoom(Long id, Long debateRoomId){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("유저가 없습니다. id=" + id));

        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id" + debateRoomId));

        user.enterDebateRoom(debateRoom);

        return id;
    }

    @Transactional
    public Long exitDebateRoom(Long id, Long debateRoomId){
        User user = userRepository.findById(id).orElseThrow(() -> new UserException("유저가 없습니다. id=" + id));

        DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id" + debateRoomId));

        user.exitDebateRoom(debateRoom);

        return id;
    }
}
