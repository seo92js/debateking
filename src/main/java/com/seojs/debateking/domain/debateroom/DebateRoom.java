package com.seojs.debateking.domain.debateroom;

import com.seojs.debateking.domain.BaseTimeEntity;
import com.seojs.debateking.domain.speech.Speech;
import com.seojs.debateking.domain.topic.Topic;
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

    @OneToMany(mappedBy = "debateRoom")
    private List<User> spectors = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "pros_id")
    private User pros;
    @OneToOne
    @JoinColumn(name = "cons_id")
    private User cons;

    @OneToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "debateRoom")
    private List<Speech> speech = new ArrayList<>();

    private String title;
    private int speakingTime;
    private int discussionTime;

    @Builder
    public DebateRoom(User user, Topic topic, String title, int speakingTime, int discussionTime){
        this.owner = user;
        this.pros = user;
        this.topic = topic;
        this.title = title;
        user.createDebateRoom(this);
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }

    public void addSpector(User user){
        this.spectors.add(user);
    }

    public void removeSpector(User user){
        this.spectors.remove(user);
    }

    public void update(String title, int speakingTime, int discussionTime){
        this.title = title;
        this.speakingTime = speakingTime;
        this.discussionTime = discussionTime;
    }

    public void updatePosition(User pros, User cons){
        this.pros = pros;
        this.cons = cons;
    }
}
