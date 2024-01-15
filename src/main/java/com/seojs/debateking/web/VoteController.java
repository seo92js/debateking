package com.seojs.debateking.web;

import com.seojs.debateking.service.vote.VoteService;
import com.seojs.debateking.web.dto.VoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class VoteController {
    private final VoteService voteService;

    @GetMapping("/vote/{debateRoomId}")
    public String vote(Model model, @PathVariable Long debateRoomId) {
        model.addAttribute("voteDto", new VoteDto());
        model.addAttribute("debateRoomId", debateRoomId);
        return "vote";
    }

    @PostMapping("/vote/{debateRoomId}")
    public String voteSave(@ModelAttribute VoteDto voteDto) {
        voteService.save(voteDto);

        return "redirect:/success";
    }
}
