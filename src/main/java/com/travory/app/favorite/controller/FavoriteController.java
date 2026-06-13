package com.travory.app.favorite.controller;

import com.travory.app.favorite.service.FavoriteService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/posts/{postId}/favorites")
    public String favorite(@PathVariable Long postId,
                           HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        favoriteService.favoritePost(postId, loginUser.getId());

        return "redirect:/posts/" + postId;
    }

    @PostMapping("/posts/{postId}/favorites/delete")
    public String unfavorite(@PathVariable Long postId,
                             HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        favoriteService.unfavoritePost(postId, loginUser.getId());

        return "redirect:/posts/" + postId;
    }

    @GetMapping("/favorites")
    public String favorites(HttpSession session,
                            Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute(
                "favoriteList",
                favoriteService.getFavoritePosts(loginUser.getId())
        );

        return "favorite/list";
    }
}
