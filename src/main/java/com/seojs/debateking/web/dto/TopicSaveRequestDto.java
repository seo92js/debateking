package com.seojs.debateking.web.dto;

import com.seojs.debateking.domain.topic.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TopicSaveRequestDto {
    private Category category;
    private String name;

    @Builder
    public TopicSaveRequestDto(Category category, String name){
        this.category = category;
        this.name = name;
    }
}
