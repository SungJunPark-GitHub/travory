package com.travory.app.comment.controller;

import com.travory.app.comment.dto.CommentDto;
import com.travory.app.comment.service.CommentService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/create")
    public String create(CommentDto commentDto,
                         HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        commentDto.setUserId(loginUser.getId());

        commentService.createComment(commentDto);

        return "redirect:/posts/" + commentDto.getPostId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         @RequestParam Long postId,
                         HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        CommentDto comment =
                commentService.getCommentById(id);

        if (!comment.getUserId().equals(loginUser.getId())) {
            return "redirect:/posts/" + postId;
        }

        commentService.deleteComment(id);

        return "redirect:/posts/" + postId;
    }
}
