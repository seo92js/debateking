package com.seojs.debateking.domain.vote;

public interface VoteRepositoryCustom {
    int countByPros(Long debateRoomId);
    int countByCons(Long debateRoomId);

    void deleteByDebateRoomId(Long debateRoomId);
}
