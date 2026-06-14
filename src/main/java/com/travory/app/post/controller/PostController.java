package com.travory.app.post.controller;

import com.travory.app.post.dto.PostDto;
import com.travory.app.post.service.PostService;
import com.travory.app.chat.service.ChatService;
import com.travory.app.companion.service.CompanionService;
import com.travory.app.favorite.service.FavoriteService;
import com.travory.app.itinerary.service.ItineraryService;
import com.travory.app.postlike.service.PostLikeService;
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
    private final PostLikeService postLikeService;
    private final FavoriteService favoriteService;
    private final CompanionService companionService;
    private final ItineraryService itineraryService;
    private final ChatService chatService;

    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(required = false) String keyword,
                       @RequestParam(defaultValue = "latest") String sort,
                       Model model) {

        String searchKeyword = keyword == null ? null : keyword.trim();
        String sortType = postService.getSortType(sort);
        int currentPage = Math.max(page, 1);
        int size = 10;
        int totalPosts = postService.countPosts(searchKeyword);
        int totalPages = (int) Math.ceil((double) totalPosts / size);

        if (totalPages == 0) {
            totalPages = 1;
        }

        if (currentPage > totalPages) {
            currentPage = totalPages;
        }

        int startPage = Math.max(currentPage - 2, 1);
        int endPage = Math.min(startPage + 4, totalPages);
        startPage = Math.max(endPage - 4, 1);

        model.addAttribute("postList",
                postService.getPostList(searchKeyword, sortType, currentPage, size));
        model.addAttribute("keyword", searchKeyword);
        model.addAttribute("sort", sortType);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

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
                         HttpSession session,
                         Model model) {

        model.addAttribute(
                "post",
                postService.getPostDetail(id)
        );

        model.addAttribute(
                "author",
                postService.getPostAuthor(id)
        );

        model.addAttribute(
                "commentList",
                commentService.getComments(id)
        );

        model.addAttribute(
                "likeCount",
                postLikeService.getLikeCount(id)
        );

        model.addAttribute(
                "favoriteCount",
                favoriteService.getFavoriteCount(id)
        );

        model.addAttribute(
                "participantCount",
                companionService.getParticipantCount(id)
        );

        model.addAttribute(
                "itineraryDays",
                itineraryService.getItinerary(id)
        );

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        boolean liked =
                loginUser != null &&
                        postLikeService.isLikedByUser(id, loginUser.getId());

        boolean favorited =
                loginUser != null &&
                        favoriteService.isFavoritedByUser(id, loginUser.getId());

        boolean applied =
                loginUser != null &&
                        companionService.hasRequested(id, loginUser.getId());

        String companionRequestStatus =
                loginUser != null
                        ? companionService.getRequestStatus(id, loginUser.getId())
                        : null;

        boolean chatAccessible =
                loginUser != null &&
                        chatService.canAccessChat(id, loginUser.getId());

        model.addAttribute("liked", liked);
        model.addAttribute("favorited", favorited);
        model.addAttribute("applied", applied);
        model.addAttribute("companionRequestStatus", companionRequestStatus);
        model.addAttribute("chatAccessible", chatAccessible);

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

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               HttpSession session) {

        UserDto loginUser = (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        PostDto post = postService.getPostById(id);

        if (!post.getUserId().equals(loginUser.getId())) {
            return "redirect:/posts/" + id;
        }

        postService.updatePostStatus(id, loginUser.getId(), status);

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
