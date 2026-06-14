package com.travory.app.chat.controller;

import com.travory.app.chat.service.ChatService;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.service.PostService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final PostService postService;

    @GetMapping("/mypage/chats")
    public String chatRooms(HttpSession session,
                            Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute(
                "chatRoomList",
                chatService.getChatRooms(loginUser.getId())
        );

        return "chat/list";
    }

    @GetMapping("/posts/{postId}/chat")
    public String chat(@PathVariable Long postId,
                       HttpSession session,
                       Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        if (!chatService.canAccessChat(postId, loginUser.getId())) {
            return "redirect:/posts/" + postId;
        }

        PostDto post =
                postService.getPostById(postId);

        chatService.markAsRead(postId, loginUser.getId());

        model.addAttribute("post", post);
        model.addAttribute("messageList", chatService.getMessages(postId));

        return "chat/room";
    }
}
