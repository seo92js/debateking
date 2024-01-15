package com.seojs.debateking.web;

import com.seojs.debateking.config.security.PrincipalDetails;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.exception.UserException;
import com.seojs.debateking.service.user.UserService;
import com.seojs.debateking.web.dto.UserResponseDto;
import com.seojs.debateking.web.dto.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/user/save")
    public String userSaveWindow(Model model){
        model.addAttribute("userSaveRequestDto", new UserSaveRequestDto("", ""));
        return "user-save";
    }

    @PostMapping("/user/save")
    public String userSave(@ModelAttribute @Valid UserSaveRequestDto userSaveRequestDto, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            log.error("errors={}", bindingResult);
            return "user-save";
        }

        userService.save(userSaveRequestDto);

        return "redirect:/";
    }

    @GetMapping("/user")
    public String user(Authentication authentication, Model model) {
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        User user = userRepository.findByUsername(principalDetails.getUsername()).orElseThrow(() -> new UserException("유저가 없습니다.username=" + principalDetails.getUsername()));

        model.addAttribute("userResponseDto", new UserResponseDto(user));

        return "user";
    }
}
