package com.seojs.debateking.web;

import com.seojs.debateking.domain.topic.Category;
import com.seojs.debateking.service.topic.TopicService;
import com.seojs.debateking.web.dto.TopicResponseDto;
import com.seojs.debateking.web.dto.TopicSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TopicApiController {
    private final TopicService topicService;

    @PostMapping("/api/v1/topic")
    public Long save(@RequestBody TopicSaveRequestDto topicSaveRequestDto){
        return topicService.save(topicSaveRequestDto);
    }

    @GetMapping("/api/v1/topic/{category}")
    public List<TopicResponseDto> findByCategory(@PathVariable Category category){
        return topicService.findByCategory(category);
    }
}
