package com.seojs.debateking.service.topic;

import com.seojs.debateking.domain.topic.Category;
import com.seojs.debateking.domain.topic.Topic;
import com.seojs.debateking.domain.topic.TopicRepository;
import com.seojs.debateking.web.dto.TopicResponseDto;
import com.seojs.debateking.web.dto.TopicSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TopicService {
    private final TopicRepository topicRepository;

    @Transactional
    public Long save(TopicSaveRequestDto topicSaveRequestDto){
        Topic topic = Topic.builder()
                .category(topicSaveRequestDto.getCategory())
                .name(topicSaveRequestDto.getName())
                .build();

        return topicRepository.save(topic).getId();
    }

    @Transactional
    public List<TopicResponseDto> findByCategory(Category category){
        return topicRepository.findByCategory(category).stream()
                .map(TopicResponseDto::new)
                .collect(Collectors.toList());
    }
}
