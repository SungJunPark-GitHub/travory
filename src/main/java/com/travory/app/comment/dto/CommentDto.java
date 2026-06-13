package com.travory.app.comment.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDto {

    private Long id;
    private Long postId;
    private Long userId;
    private String content;
    private LocalDateTime createdAt;

    private String userName;
}