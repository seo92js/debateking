package com.seojs.debateking.domain.user;

import com.seojs.debateking.domain.BaseTimeEntity;
import com.seojs.debateking.domain.debateroom.DebateRoom;
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
    @Column(name = "user_id")
    private Long id;

    private String username;
    private String password;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debateroom_id")
    private DebateRoom debateRoom;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    private int win;
    private int lose;
    private int draw;

    @Builder
    public User(String username, String password){
        this.username = username;
        this.password = password;
        this.image = null;
        this.debateRoom = null;
        this.win = 0;
        this.lose = 0;
        this.draw = 0;
        this.role = Role.USER;
    }

    public void update(String name, String password){
        this.username = name;
        this.password = password;
    }

    public void createDebateRoom(DebateRoom debateRoom){
        this.debateRoom = debateRoom;
    }

    public void enterDebateRoom(DebateRoom debateRoom){
        this.debateRoom = debateRoom;
        debateRoom.addSpector(this);
    }

    public void exitDebateRoom(DebateRoom debateRoom){
        this.debateRoom = null;
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
