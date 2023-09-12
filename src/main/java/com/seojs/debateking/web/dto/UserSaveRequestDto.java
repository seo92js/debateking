package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {
    private String username;
    private String password;

    @Builder
    public UserSaveRequestDto(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .image(null)
                .win(0)
                .lose(0)
                .draw(0)
                .build();
    }
}
