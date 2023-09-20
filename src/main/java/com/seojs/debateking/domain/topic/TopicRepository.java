package com.seojs.debateking.domain.topic;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findByCategory(Category category);

    Topic findByName(String name);
}
