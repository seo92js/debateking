package com.seojs.debateking.domain.speech;

import com.seojs.debateking.domain.BaseTimeEntity;
import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Speech extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "debateroom_id")
    private DebateRoom debateRoom;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String speech;

    @Builder
    public Speech(DebateRoom debateRoom, User user, String speech){
        this.debateRoom = debateRoom;
        this.user = user;
        this.speech = speech;
    }
}
