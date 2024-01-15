package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final DebateRoomService debateRoomService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("debateRooms", debateRoomService.findAll());
        return "index";
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
