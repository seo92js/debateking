package com.seojs.debateking.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VoteController {
    @GetMapping("/vote")
    public String vote() {
        return "vote";
    }
}
