package com.seojs.debateking.web;

import com.seojs.debateking.domain.topic.Category;
import com.seojs.debateking.web.dto.TopicSaveRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class TopicController {
    @GetMapping("/topic/save")
    public String topicSave(Model model){
        List<Category> categories = Arrays.asList(Category.values());

        model.addAttribute("categories", categories);
        model.addAttribute("topicSaveRequestDto", TopicSaveRequestDto.builder()
                        .category(null)
                        .name("")
                        .build());
        return "topic-save";
    }
}
