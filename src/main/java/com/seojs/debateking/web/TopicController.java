package com.seojs.debateking.web;

import com.seojs.debateking.domain.topic.Category;
import com.seojs.debateking.service.topic.TopicService;
import com.seojs.debateking.web.dto.TopicSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class TopicController {
    private final TopicService topicService;

    @GetMapping("/topic/save")
    public String topic(Model model){
        List<Category> categories = Arrays.asList(Category.values());

        model.addAttribute("categories", categories);

        return "topic-save";
    }

    @PostMapping("/topic/save")
    public String topicSave(@ModelAttribute TopicSaveRequestDto topicSaveRequestDto){
        topicService.save(topicSaveRequestDto);

        return "redirect:/";
    }
}
