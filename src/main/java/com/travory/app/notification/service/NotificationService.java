package com.travory.app.notification.service;

import com.travory.app.notification.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    void createNotification(Long receiverUserId,
                            Long senderUserId,
                            String type,
                            String message,
                            String targetUrl);

    List<NotificationDto> getNotifications(Long userId);

    int getUnreadCount(Long userId);

    void markAsRead(Long id, Long userId);

    void markAllAsRead(Long userId);
}
