package com.seojs.debateking.domain.debateroom;

import com.seojs.debateking.domain.BaseTimeEntity;
import com.seojs.debateking.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class DebateRoom extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debateroom_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "debateRoom", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<User> spectors = new ArrayList<>();

    private String title;
    private int speakingTime;
    private int discussionTime;

    @Builder
    public DebateRoom(User user, String title, int speakingTime, int discussionTime){
        this.owner = user;
        this.title = title;
        user.createDebateRoom(this);
        addSpector(user);
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }

    public void addSpector(User user){
        this.spectors.add(user);
    }
}
