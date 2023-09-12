package com.seojs.debateking.domain.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @AfterEach
    public void clean(){
        userRepository.deleteAll();
    }

    @Test
    public void 회원가입(){
        //given
        String username = "seo";
        String password = "1";

        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        //when
        userRepository.save(user);

        //then
        List<User> all = userRepository.findAll();
        assertThat(all.size()).isEqualTo(1);
    }

    @Test
    public void auditing() {
        //given
        LocalDateTime now = LocalDateTime.of(2023,9,11,0,0,0);

        userRepository.save(User.builder()
                .username("seo")
                .password("1")
                .build());

        //when
        List<User> all = userRepository.findAll();
        System.out.println(all.get(0).getCreatedDate());

        //then
        assertThat(all.get(0).getCreatedDate()).isAfter(now);
        assertThat(all.get(0).getModifiedDate()).isAfter(now);
    }
}