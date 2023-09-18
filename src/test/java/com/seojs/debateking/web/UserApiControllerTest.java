package com.seojs.debateking.web;

import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.UserResponseDto;
import com.seojs.debateking.web.dto.UserSaveRequestDto;
import com.seojs.debateking.web.dto.UserUpdateRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @AfterEach
    public void clean(){
        userRepository.deleteAll();
    }

    @Test
    public void save() {
        //given
        String username = "seo92js";
        String password = "seo92js";

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .username(username)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/api/v1/user";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, userSaveRequestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);
        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getUsername()).isEqualTo(username);
        if (passwordEncoder.matches(password, all.get(0).getPassword()))
            System.out.println("ok");
    }

    @Test
    public void update(){
        //given
        User saved = userRepository.save(User.builder()
                .username("seo92js")
                .password("seo92js")
                .build());

        String expectedUsername = "seo92js2";
        String expectedPassword ="seo92js2";

        UserUpdateRequestDto userUpdateRequestDto = UserUpdateRequestDto.builder()
                .username(expectedUsername)
                .password(expectedPassword)
                .build();

        String url = "http://localhost:" + port + "/api/v1/user/" + saved.getId();
        HttpEntity<UserUpdateRequestDto> requestEntity = new HttpEntity<>(userUpdateRequestDto);

        //when
        ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Long.class);
        //then

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<User> all = userRepository.findAll();
        assertThat(all.get(0).getUsername()).isEqualTo(expectedUsername);
        assertThat(all.get(0).getPassword()).isEqualTo(expectedPassword);
    }

    @Test
    public void get(){
        //given
        User saved = userRepository.save(User.builder()
                .username("seo92js")
                .password("seo92js")
                .build());

        String url = "http://localhost:" + port + "/api/v1/user/" + saved.getId();

        //when
        ResponseEntity<UserResponseDto> responseEntity = restTemplate.getForEntity(url, UserResponseDto.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getId()).isEqualTo(saved.getId());
        assertThat(responseEntity.getBody().getUsername()).isEqualTo(saved.getUsername());
    }
}