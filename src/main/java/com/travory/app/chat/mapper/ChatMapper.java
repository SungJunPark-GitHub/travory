package com.travory.app.chat.mapper;

import com.travory.app.chat.dto.ChatMessageDto;
import com.travory.app.chat.dto.ChatRoomDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatMapper {

    void insertMessage(ChatMessageDto chatMessageDto);

    ChatMessageDto findById(Long id);

    List<ChatMessageDto> findByPostId(Long postId);

    List<ChatRoomDto> findChatRoomsByUserId(Long userId);

    void markAsRead(@Param("postId") Long postId,
                    @Param("userId") Long userId);

    int countUnreadMessages(@Param("postId") Long postId,
                            @Param("userId") Long userId);

    int findReadStatus(@Param("postId") Long postId,
                       @Param("userId") Long userId);
}
