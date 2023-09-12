package com.seojs.debateking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DebatekingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebatekingApplication.class, args);
	}

}
