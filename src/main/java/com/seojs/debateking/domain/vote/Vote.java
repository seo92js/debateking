package com.seojs.debateking.domain.vote;

import com.seojs.debateking.domain.BaseTimeEntity;
import com.seojs.debateking.domain.debateroom.DebateRoom;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Vote extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "debateroom_id")
    private DebateRoom debateRoom;

    private boolean prosAndCons;

    @Builder
    public Vote(DebateRoom debateRoom, boolean prosAndCons) {
        this.debateRoom = debateRoom;
        this.prosAndCons = prosAndCons;
    }
}
