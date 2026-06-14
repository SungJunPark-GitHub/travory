package com.travory.app.chat.service;

import com.travory.app.chat.dto.ChatMessageDto;
import com.travory.app.chat.dto.ChatRoomDto;

import java.util.List;

public interface ChatService {

    boolean canAccessChat(Long postId, Long userId);

    List<ChatMessageDto> getMessages(Long postId);

    ChatMessageDto saveMessage(Long postId, Long userId, String content);

    List<ChatRoomDto> getChatRooms(Long userId);

    void markAsRead(Long postId, Long userId);

    int countUnreadMessages(Long postId, Long userId);
}
