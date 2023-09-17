package com.seojs.debateking.web;

import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.web.dto.DebateRoomResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final DebateRoomService debateRoomService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("debateRooms", debateRoomService.findAll());
        return "index";
    }
}
