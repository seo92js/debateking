package com.seojs.debateking.web;

import com.seojs.debateking.domain.debateroom.DebateRoom;
import com.seojs.debateking.domain.debateroom.DebateRoomRepository;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import com.seojs.debateking.web.dto.DebateRoomUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DebateRoomApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DebateRoomRepository debateRoomRepository;

    @AfterEach
    public void clean() {
        debateRoomRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void save() {
        //given
        User user = User.builder()
                .username("seo92js")
                .password("seo92js")
                .build();

        User saved = userRepository.save(user);

        DebateRoomSaveRequestDto debateRoomSaveRequestDto = DebateRoomSaveRequestDto.builder()
                .userId(saved.getId())
                .title("title")
                .speakingTime(1)
                .discussionTime(2)
                .build();

        String url = "http://localhost:" + port + "/api/v1/debateroom";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, debateRoomSaveRequestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<DebateRoom> all = debateRoomRepository.findAll();
        assertThat(all.get(0).getTitle()).isEqualTo("title");
    }

    @Test
    public void delete() {
        //given
        User user = User.builder()
                .username("seo92js")
                .password("seo92js")
                .build();

        User saved = userRepository.save(user);

        DebateRoom debateRoom = DebateRoom.builder()
                .user(saved)
                .title("title")
                .speakingTime(1)
                .discussionTime(1)
                .build();

        DebateRoom save = debateRoomRepository.save(debateRoom);

        //when
        String url = "http://localhost:" + port + "/api/v1/debateroom/" + save.getId();

        restTemplate.delete(url);
        //then
        List<DebateRoom> all = debateRoomRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    public void update(){
        //given
//given
        User user = User.builder()
                .username("seo92js")
                .password("seo92js")
                .build();

        User saved = userRepository.save(user);

        DebateRoom debateRoom = DebateRoom.builder()
                .user(saved)
                .title("title")
                .speakingTime(1)
                .discussionTime(1)
                .build();

        DebateRoom save = debateRoomRepository.save(debateRoom);

        String expectedTitle = "title2";
        int expectedSpeakingTime=5;
        int expectedDiscussionTime=5;

        DebateRoomUpdateRequestDto debateRoomUpdateRequestDto = DebateRoomUpdateRequestDto.builder()
                .title(expectedTitle)
                .discussionTime(expectedSpeakingTime)
                .speakingTime(expectedDiscussionTime)
                .build();

        String url = "http://localhost:" + port + "/api/v1/debateroom/" + save.getId();

        //when
        restTemplate.put(url, debateRoomUpdateRequestDto, Long.class);

        List<DebateRoom> all = debateRoomRepository.findAll();
        //then
        assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
        assertThat(all.get(0).getSpeakingTime()).isEqualTo(expectedSpeakingTime);
        assertThat(all.get(0).getDiscussionTime()).isEqualTo(expectedDiscussionTime);
    }
}