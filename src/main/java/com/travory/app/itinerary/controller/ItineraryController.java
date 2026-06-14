package com.travory.app.itinerary.controller;

import com.travory.app.itinerary.service.ItineraryService;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.service.PostService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ItineraryController {

    private final ItineraryService itineraryService;
    private final PostService postService;

    @GetMapping("/posts/{postId}/itinerary")
    public String manage(@PathVariable Long postId,
                         HttpSession session,
                         Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        if (!itineraryService.isPostOwner(postId, loginUser.getId())) {
            return "redirect:/posts/" + postId;
        }

        PostDto post =
                postService.getPostById(postId);

        model.addAttribute("post", post);
        model.addAttribute(
                "itineraryDays",
                itineraryService.getItinerary(postId)
        );

        return "itinerary/manage";
    }

    @PostMapping("/posts/{postId}/itinerary/days")
    public String createDay(@PathVariable Long postId,
                            HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        itineraryService.createDay(postId, loginUser.getId());

        return "redirect:/posts/" + postId + "/itinerary";
    }

    @PostMapping("/posts/{postId}/itinerary/items")
    public String createItem(@PathVariable Long postId,
                             @RequestParam Long dayId,
                             @RequestParam String placeName,
                             @RequestParam(required = false) String memo,
                             @RequestParam Integer orderNumber,
                             HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        itineraryService.createItem(
                postId,
                loginUser.getId(),
                dayId,
                placeName,
                memo,
                orderNumber
        );

        return "redirect:/posts/" + postId + "/itinerary";
    }

    @PostMapping("/posts/{postId}/itinerary/items/{itemId}/delete")
    public String deleteItem(@PathVariable Long postId,
                             @PathVariable Long itemId,
                             HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        itineraryService.deleteItem(postId, loginUser.getId(), itemId);

        return "redirect:/posts/" + postId + "/itinerary";
    }
}
