package com.seojs.debateking.web;

import com.seojs.debateking.web.dto.UserSaveRequestDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/user/save")
    public String userSave(Model model){
        model.addAttribute("userSaveRequestDto", new UserSaveRequestDto("", ""));
        return "user-save";
    }
}
