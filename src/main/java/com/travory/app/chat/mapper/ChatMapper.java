package com.travory.app.chat.mapper;

import com.travory.app.chat.dto.ChatMessageDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {

    void insertMessage(ChatMessageDto chatMessageDto);

    ChatMessageDto findById(Long id);

    List<ChatMessageDto> findByPostId(Long postId);
}
