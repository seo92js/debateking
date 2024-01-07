package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserRecordResponseDto {
    private int win;
    private int lose;
    private int draw;

    public UserRecordResponseDto(User user){
        this.win = user.getWin();
        this.lose = user.getLose();
        this.draw = user.getDraw();
    }
}
