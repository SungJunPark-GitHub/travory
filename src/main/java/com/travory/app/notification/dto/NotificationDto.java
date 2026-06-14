package com.travory.app.notification.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {

    private Long id;

    private Long userId;

    private String type;

    private String message;

    private String targetUrl;

    private Boolean isRead;

    private LocalDateTime createdAt;
}
