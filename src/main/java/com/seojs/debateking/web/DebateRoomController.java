package com.seojs.debateking.web;

import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class DebateRoomController {

    @GetMapping("/debateroom/save")
    public String debateRoomSave(Model model){
        //임시, Login user id 넣어야함
        model.addAttribute("debateRoomSaveRequestDto", new DebateRoomSaveRequestDto(1L, "", 0, 0));
        return "debateroom-save";
    }
}
