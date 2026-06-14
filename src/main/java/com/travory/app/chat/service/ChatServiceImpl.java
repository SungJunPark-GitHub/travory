package com.travory.app.chat.service;

import com.travory.app.chat.dto.ChatMessageDto;
import com.travory.app.chat.dto.ChatRoomDto;
import com.travory.app.chat.mapper.ChatMapper;
import com.travory.app.companion.service.CompanionService;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private static final int MAX_MESSAGE_LENGTH = 1000;

    private final ChatMapper chatMapper;
    private final PostMapper postMapper;
    private final CompanionService companionService;

    @Override
    public boolean canAccessChat(Long postId, Long userId) {
        if (userId == null) {
            return false;
        }

        PostDto post =
                postMapper.findById(postId);

        if (post == null) {
            return false;
        }

        return post.getUserId().equals(userId)
                || companionService.hasApprovedRequest(postId, userId);
    }

    @Override
    public List<ChatMessageDto> getMessages(Long postId) {
        return chatMapper.findByPostId(postId);
    }

    @Override
    public ChatMessageDto saveMessage(Long postId, Long userId, String content) {
        if (!canAccessChat(postId, userId)) {
            return null;
        }

        if (content == null || content.trim().isEmpty()) {
            return null;
        }

        String trimmedContent =
                content.trim();

        if (trimmedContent.length() > MAX_MESSAGE_LENGTH) {
            return null;
        }

        ChatMessageDto chatMessage =
                ChatMessageDto.builder()
                        .postId(postId)
                        .userId(userId)
                        .content(trimmedContent)
                        .build();

        chatMapper.insertMessage(chatMessage);

        return chatMapper.findById(chatMessage.getId());
    }

    @Override
    public List<ChatRoomDto> getChatRooms(Long userId) {
        return chatMapper.findChatRoomsByUserId(userId);
    }
}
