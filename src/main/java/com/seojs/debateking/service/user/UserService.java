package com.seojs.debateking.service.user;

import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.UserResponseDto;
import com.seojs.debateking.web.dto.UserSaveRequestDto;
import com.seojs.debateking.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public Long save(UserSaveRequestDto userSaveRequestDto){

        return userRepository.save(userSaveRequestDto.toEntity()).getId();
    }

    @Transactional
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + id));

        return new UserResponseDto(user);
    }

    @Transactional
    public Long update(Long id, UserUpdateRequestDto userUpdateRequestDto){
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. id=" + id));

        user.update(userUpdateRequestDto.getUsername(), userUpdateRequestDto.getPassword());

        return id;
    }
}
