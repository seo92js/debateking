package com.seojs.debateking.web;

import com.seojs.debateking.config.security.PrincipalDetails;
import com.seojs.debateking.domain.topic.Category;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DebateRoomController {
    private final UserRepository userRepository;

    @GetMapping("/debateroom/save")
    public String debateRoomSave(Model model, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        User loginUser = userRepository.findByUsername(principalDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. username=" + principalDetails.getUsername()));

        List<Category> categories = Arrays.asList(Category.values());

        model.addAttribute("categories", categories);
        model.addAttribute("debateRoomSaveRequestDto", new DebateRoomSaveRequestDto(loginUser.getId(), null,"", 0, 0));

        return "debateroom-save";
    }
}
