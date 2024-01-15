package com.seojs.debateking.domain.vote;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

import static com.seojs.debateking.domain.vote.QVote.vote;

public class VoteRepositoryImpl implements VoteRepositoryCustom{
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public VoteRepositoryImpl(EntityManager em) {
        this.em = em;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public int countByPros(Long debateRoomId) {
        return queryFactory
                .select(vote.prosAndCons)
                .from(vote)
                .where(vote.debateRoom.id.eq(debateRoomId).and(vote.prosAndCons.eq(true)))
                .fetch().size();
    }

    @Override
    public int countByCons(Long debateRoomId) {
        return queryFactory
                .select(vote.prosAndCons)
                .from(vote)
                .where(vote.debateRoom.id.eq(debateRoomId).and(vote.prosAndCons.eq(false)))
                .fetch().size();
    }

    @Override
    public void deleteByDebateRoomId(Long debateRoomId) {
        queryFactory
                .delete(vote)
                .where(vote.debateRoom.id.eq(debateRoomId))
                .execute();
    }
}
