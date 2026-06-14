package com.travory.app.chat.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomDto {

    private Long postId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String role;

    private String latestMessageContent;

    private LocalDateTime latestMessageCreatedAt;
}
