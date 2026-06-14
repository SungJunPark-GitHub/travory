package com.travory.app.chat.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageDto {

    private Long id;

    private Long postId;

    private Long userId;

    private String content;

    private LocalDateTime createdAt;

    private String userName;

    private String profileImage;
}
