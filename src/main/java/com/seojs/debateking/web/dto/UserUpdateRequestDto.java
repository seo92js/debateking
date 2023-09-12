package com.seojs.debateking.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserUpdateRequestDto {
    private String username;
    private String password;

    @Builder
    public UserUpdateRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }
}
