package com.seojs.debateking.service.topic;

import com.seojs.debateking.domain.topic.Topic;
import com.seojs.debateking.domain.topic.TopicRepository;
import com.seojs.debateking.web.dto.TopicSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
