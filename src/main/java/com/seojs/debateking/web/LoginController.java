package com.seojs.debateking.web;

import com.seojs.debateking.service.user.UserService;
import com.seojs.debateking.web.dto.UserLoginRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {
    private final UserService userService;

    @GetMapping("/login")
    public String userLogin(Model model){
        model.addAttribute("userLoginRequestDto", new UserLoginRequestDto("", ""));
        return "user-login";
    }

    @PostMapping("/user/login")
    public void login(UserLoginRequestDto userLoginRequestDto){
        userService.login(userLoginRequestDto);
    }
}
