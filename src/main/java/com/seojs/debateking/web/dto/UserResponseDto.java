package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long id;
    private String username;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
    }
}
