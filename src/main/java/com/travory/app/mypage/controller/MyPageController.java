package com.travory.app.mypage.controller;

import com.travory.app.mypage.service.MyPageService;
import com.travory.app.user.dto.UserDto;
import com.travory.app.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageService myPageService;
    private final UserService userService;

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

    @GetMapping("/mypage/profile/edit")
    public String profileEditPage(HttpSession session,
                                  Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute(
                "user",
                userService.getUserById(loginUser.getId())
        );

        return "user/profile-edit";
    }

    @PostMapping("/mypage/profile/edit")
    public String profileEdit(UserDto userDto,
                              @RequestParam(value = "profileImageFile", required = false)
                              MultipartFile profileImageFile,
                              HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        userDto.setId(loginUser.getId());
        userService.updateProfile(userDto, profileImageFile);

        UserDto updatedUser =
                userService.getUserById(loginUser.getId());

        session.setAttribute("loginUser", updatedUser);

        return "redirect:/mypage";
    }
}
