package com.travory.app.notification;

import com.travory.app.notification.service.NotificationService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
@RequiredArgsConstructor
public class NotificationModelAdvice {

    private final NotificationService notificationService;

    @ModelAttribute("unreadNotificationCount")
    public int unreadNotificationCount(HttpSession session) {
        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return 0;
        }

        return notificationService.getUnreadCount(loginUser.getId());
    }
}
