package com.seojs.debateking.domain.topic;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private Category category;
    private String name;

    @Builder
    public Topic(Category category, String name){
        this.category = category;
        this.name = name;
    }
}
