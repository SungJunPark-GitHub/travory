package com.travory.app.mypage.controller;

import com.travory.app.mypage.service.MyPageService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping("/mypage")
    public String myPage(HttpSession session,
                         Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute("loginUser", loginUser);
        model.addAttribute(
                "myPostList",
                myPageService.getMyPosts(loginUser.getId())
        );
        model.addAttribute(
                "favoritePostList",
                myPageService.getFavoritePosts(loginUser.getId())
        );
        model.addAttribute(
                "companionRequestList",
                myPageService.getCompanionRequests(loginUser.getId())
        );

        return "mypage/index";
    }
}
