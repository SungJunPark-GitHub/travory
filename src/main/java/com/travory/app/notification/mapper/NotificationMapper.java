package com.travory.app.notification.mapper;

import com.travory.app.notification.dto.NotificationDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NotificationMapper {

    void insertNotification(@Param("userId") Long userId,
                            @Param("type") String type,
                            @Param("message") String message,
                            @Param("targetUrl") String targetUrl);

    List<NotificationDto> findByUserId(Long userId);

    int countUnreadByUserId(Long userId);

    void markAsRead(@Param("id") Long id,
                    @Param("userId") Long userId);

    void markAllAsRead(Long userId);
}
