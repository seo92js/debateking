package com.seojs.debateking.service.vote;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.vote.Vote;
import com.seojs.debateking.domain.vote.VoteRepository;
import com.seojs.debateking.exception.DebateRoomException;
import com.seojs.debateking.web.dto.VoteDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final DebateRoomRepository debateRoomRepository;

    @Transactional
    public Long save(VoteDto voteDto) {
        DebateRoom debateRoom = debateRoomRepository.findById(voteDto.getDebateRoomId()).orElseThrow(() -> new DebateRoomException("토론방이 없습니다. id=" + voteDto.getDebateRoomId()));

        Vote vote = Vote.builder()
                .debateRoom(debateRoom)
                .prosAndCons(voteDto.isProsCons())
                .build();

        return voteRepository.save(vote).getId();
    }

    @Transactional
    public int debateResult(Long debateRoomId) {
        //prosCount
        int prosCount = voteRepository.countByPros(debateRoomId);
        //consCount
        int consCount = voteRepository.countByCons(debateRoomId);

        if (prosCount > consCount)
            return 0;
        else if (prosCount < consCount)
            return 1;
        else
            return -1;
    }

    @Transactional
    public void deleteByDebateRoomId(Long debateRoomId) {
        voteRepository.deleteByDebateRoomId(debateRoomId);
    }
}
