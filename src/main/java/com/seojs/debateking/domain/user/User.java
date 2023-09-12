package com.seojs.debateking.domain.user;

import com.seojs.debateking.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String image;
    private int win;
    private int lose;
    private int draw;

    @Builder
    public User(String username, String password, String image, int win, int lose, int draw){
        this.username = username;
        this.password = password;
        this.image = image;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
    }

    public void update(String name, String password){
        this.username = name;
        this.password = password;
    }

    public void win(){
        this.win += 1;
    }

    public void lose(){
        this.lose += 1;
    }

    public void draw(){
        this.draw += 1;
    }
}
