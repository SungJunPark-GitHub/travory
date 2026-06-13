package com.travory.app.postlike.controller;

import com.travory.app.postlike.service.PostLikeService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts/{postId}/likes")
@RequiredArgsConstructor
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public String like(@PathVariable Long postId,
                       HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        postLikeService.likePost(postId, loginUser.getId());

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/delete")
    public String unlike(@PathVariable Long postId,
                         HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        postLikeService.unlikePost(postId, loginUser.getId());

        return "redirect:/posts/" + postId;
    }
}
