package com.seojs.debateking.web;

import com.seojs.debateking.service.user.UserService;
import com.seojs.debateking.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/user/save")
    public String user(Model model){
        model.addAttribute("userSaveRequestDto", new UserSaveRequestDto("", ""));
        return "user-save";
    }

    @PostMapping("/user/save")
    public String userSave(@ModelAttribute @Valid UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return "user-save";
        }

        userService.save(userSaveRequestDto);

        return "redirect:/";
    }
}
