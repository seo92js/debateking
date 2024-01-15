package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String username;
    private int win;
    private int lose;
    private int draw;

    public UserResponseDto(User user){
        this.id = user.getId();
        this.username = user.getUsername();
        this.win = user.getWin();
        this.lose = user.getLose();
        this.draw = user.getDraw();
    }
}
