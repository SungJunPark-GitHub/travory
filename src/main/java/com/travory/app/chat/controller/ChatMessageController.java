package com.travory.app.chat.controller;

import com.travory.app.chat.dto.ChatMessageDto;
import com.travory.app.chat.dto.ChatMessageRequest;
import com.travory.app.chat.service.ChatService;
import com.travory.app.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/posts/{postId}/chat")
    public void sendMessage(@DestinationVariable Long postId,
                            ChatMessageRequest request,
                            SimpMessageHeaderAccessor headerAccessor) {

        if (request == null) {
            return;
        }

        Map<String, Object> sessionAttributes =
                headerAccessor.getSessionAttributes();

        if (sessionAttributes == null) {
            return;
        }

        UserDto loginUser =
                (UserDto) sessionAttributes.get("loginUser");

        if (loginUser == null) {
            return;
        }

        ChatMessageDto savedMessage =
                chatService.saveMessage(
                        postId,
                        loginUser.getId(),
                        request.getContent()
                );

        if (savedMessage == null) {
            return;
        }

        messagingTemplate.convertAndSend(
                "/topic/posts/" + postId + "/chat",
                savedMessage
        );
    }
}
