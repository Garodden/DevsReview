package com.example.KeyboardArenaProject.controller.user;

import com.example.KeyboardArenaProject.dto.user.MyPageInformation;
import com.example.KeyboardArenaProject.service.user.MyPageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Controller
public class MyPageController {
    private MyPageService myPageService;

    public MyPageController(MyPageService myPageService) {
        this.myPageService = myPageService;
    }

    @GetMapping("/mypage/{userId}")
    public String getUserInfo(@PathVariable String userId, Model model) {
        MyPageInformation userInfo = myPageService.getUserInfo(userId);
        model.addAttribute("userInfo", userInfo);
        return "mypage";
    }
}
