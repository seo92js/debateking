package com.seojs.debateking.web;

import com.seojs.debateking.web.dto.HelloResposeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello/dto")
    public HelloResposeDto helloResposeDto(@RequestParam("username") String username, @RequestParam("amount") int amount){
        return new HelloResposeDto(username, amount);
    }
}
