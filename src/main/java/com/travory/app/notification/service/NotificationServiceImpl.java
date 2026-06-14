package com.travory.app.notification.service;

import com.travory.app.notification.dto.NotificationDto;
import com.travory.app.notification.mapper.NotificationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Override
    public void createNotification(Long receiverUserId,
                                   Long senderUserId,
                                   String type,
                                   String message,
                                   String targetUrl) {

        if (receiverUserId == null) {
            return;
        }

        if (receiverUserId.equals(senderUserId)) {
            return;
        }

        notificationMapper.insertNotification(
                receiverUserId,
                type,
                message,
                targetUrl
        );
    }

    @Override
    public List<NotificationDto> getNotifications(Long userId) {
        return notificationMapper.findByUserId(userId);
    }

    @Override
    public int getUnreadCount(Long userId) {
        if (userId == null) {
            return 0;
        }

        return notificationMapper.countUnreadByUserId(userId);
    }

    @Override
    public void markAsRead(Long id, Long userId) {
        notificationMapper.markAsRead(id, userId);
    }

    @Override
    public void markAllAsRead(Long userId) {
        notificationMapper.markAllAsRead(userId);
    }
}
