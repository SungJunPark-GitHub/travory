package com.travory.app.companion.controller;

import com.travory.app.companion.service.CompanionService;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.service.PostService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CompanionController {

    private final CompanionService companionService;
    private final PostService postService;

    @PostMapping("/posts/{postId}/requests")
    public String request(@PathVariable Long postId,
                          @RequestParam String message,
                          HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        companionService.requestCompanion(postId, loginUser.getId(), message);

        return "redirect:/posts/" + postId;
    }

    @GetMapping("/posts/{postId}/requests")
    public String requests(@PathVariable Long postId,
                           HttpSession session,
                           Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        PostDto post = postService.getPostById(postId);

        if (!post.getUserId().equals(loginUser.getId())) {
            return "redirect:/posts/" + postId;
        }

        model.addAttribute("post", post);
        model.addAttribute(
                "requestList",
                companionService.getRequests(postId, loginUser.getId())
        );
        model.addAttribute(
                "participantCount",
                companionService.getParticipantCount(postId)
        );

        return "companion/requests";
    }

    @PostMapping("/posts/{postId}/requests/{requestId}/approve")
    public String approve(@PathVariable Long postId,
                          @PathVariable Long requestId,
                          HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        companionService.approveRequest(postId, requestId, loginUser.getId());

        return "redirect:/posts/" + postId + "/requests";
    }

    @PostMapping("/posts/{postId}/requests/{requestId}/reject")
    public String reject(@PathVariable Long postId,
                         @PathVariable Long requestId,
                         HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        companionService.rejectRequest(postId, requestId, loginUser.getId());

        return "redirect:/posts/" + postId + "/requests";
    }
}
