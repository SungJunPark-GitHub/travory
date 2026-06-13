package com.travory.app.post.controller;

import com.travory.app.post.dto.PostDto;
import com.travory.app.post.service.PostService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.travory.app.comment.service.CommentService;

@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public String list(Model model) {

        model.addAttribute("postList",
                postService.getPostList());

        return "post/list";
    }

    @GetMapping("/create")
    public String createPage() {
        return "post/create";
    }

    @PostMapping("/create")
    public String create(PostDto postDto,
                         HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        postDto.setUserId(loginUser.getId());

        postService.createPost(postDto);

        return "redirect:/posts";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         Model model) {

        model.addAttribute(
                "post",
                postService.getPostDetail(id)
        );

        model.addAttribute(
                "commentList",
                commentService.getComments(id)
        );

        return "post/detail";
    }

    @GetMapping("/{id}/edit")
    public String editPage(@PathVariable Long id,
                           HttpSession session,
                           Model model) {

        UserDto loginUser = (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        PostDto post = postService.getPostById(id);

        if (!post.getUserId().equals(loginUser.getId())) {
            return "redirect:/posts";
        }

        model.addAttribute("post", post);

        return "post/edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id,
                       PostDto postDto,
                       HttpSession session) {

        UserDto loginUser = (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        postDto.setId(id);
        postDto.setUserId(loginUser.getId());

        postService.updatePost(postDto);

        return "redirect:/posts/" + id;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpSession session) {

        UserDto loginUser = (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        PostDto post = postService.getPostById(id);

        if (!post.getUserId().equals(loginUser.getId())) {
            return "redirect:/posts";
        }

        postService.deletePost(id);

        return "redirect:/posts";
    }

    private final CommentService commentService;
}
