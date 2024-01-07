package com.seojs.debateking.web;

import com.seojs.debateking.config.security.PrincipalDetails;
import com.seojs.debateking.domain.debateroom.DiscussionTime;
import com.seojs.debateking.domain.debateroom.SpeakingTime;
import com.seojs.debateking.domain.topic.Category;
import com.seojs.debateking.domain.user.User;
import com.seojs.debateking.domain.user.UserRepository;
import com.seojs.debateking.service.debateroom.DebateRoomService;
import com.seojs.debateking.service.redis.RedisMessageListener;
import com.seojs.debateking.service.redis.RedisService;
import com.seojs.debateking.service.user.UserService;
import com.seojs.debateking.web.dto.DebateRoomSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class DebateRoomController {
    private final UserRepository userRepository;
    private final UserService userService;
    private final DebateRoomService debateRoomService;
    private final RedisService redisService;
    private final RedisMessageListener redisMessageListener;

    @GetMapping("/debateroom/save")
    public String debateRoom(Model model, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        User loginUser = userRepository.findByUsername(principalDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다. username=" + principalDetails.getUsername()));

        List<Category> categories = Arrays.asList(Category.values());
        model.addAttribute("categories", categories);

        List<SpeakingTime> speakingTimes = Arrays.asList(SpeakingTime.values());
        model.addAttribute("speakingTimes", speakingTimes);

        List<DiscussionTime> discussionTimes = Arrays.asList(DiscussionTime.values());
        model.addAttribute("discussionTimes", discussionTimes);

        model.addAttribute("debateRoomSaveRequestDto", new DebateRoomSaveRequestDto(loginUser.getId(), "","", 0, 0));

        return "debateroom-save";
    }

    @PostMapping("debateroom/save")
    public String debateRoomSave(@ModelAttribute DebateRoomSaveRequestDto debateRoomSaveRequestDto){
        Long id = debateRoomService.save(debateRoomSaveRequestDto);

        redisMessageListener.enterChatRoom(id);

        return "redirect:/debateroom/" + id;
    }

    @GetMapping("/debateroom/{id}")
    public String debateRoom(@PathVariable Long id, Model model, Authentication authentication){
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        User user = userRepository.findByUsername(principalDetails.getUsername()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다.username=" + principalDetails.getUsername()));

        model.addAttribute("loginUsername", principalDetails.getUsername());
        model.addAttribute("debateRoomId", id);
        model.addAttribute("loginUserId", user.getId());

        model.addAttribute("debateRoomResponseDto", debateRoomService.findById(id));

        model.addAttribute("speeches", redisService.getSpeeches(id));
        model.addAttribute("chats", redisService.getChats(id));

        return "debateroom";
    }
}
