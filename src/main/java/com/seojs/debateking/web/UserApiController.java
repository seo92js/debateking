package com.seojs.debateking.web;

import com.seojs.debateking.service.user.UserService;
import com.seojs.debateking.web.dto.UserResponseDto;
import com.seojs.debateking.web.dto.UserSaveRequestDto;
import com.seojs.debateking.web.dto.UserUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class UserApiController {
    private final UserService userService;

    @PostMapping("/api/v1/user")
    public Long save(@RequestBody @Valid UserSaveRequestDto userSaveRequestDto){
        return userService.save(userSaveRequestDto);
    }

    @PutMapping("/api/v1/user/{id}")
    public Long update(@PathVariable Long id, @RequestBody UserUpdateRequestDto userUpdateRequestDto){
        return userService.update(id, userUpdateRequestDto);
    }

    @GetMapping("/api/v1/user/{id}")
    public UserResponseDto findById(@PathVariable Long id){
        return userService.findById(id);
    }
}
