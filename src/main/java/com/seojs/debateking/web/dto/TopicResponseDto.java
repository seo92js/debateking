package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.topic.Category;
import com.seojs.debateking.domain.topic.Topic;
import lombok.Getter;

@Getter
public class TopicResponseDto {
    private Category category;
    private String name;

    public TopicResponseDto(Topic topic){
        this.category = topic.getCategory();
        this.name = topic.getName();
    }
}
