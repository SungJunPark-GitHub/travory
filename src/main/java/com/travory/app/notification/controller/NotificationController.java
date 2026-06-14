package com.travory.app.notification.controller;

import com.travory.app.notification.service.NotificationService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/notifications")
    public String notifications(HttpSession session,
                                Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        model.addAttribute(
                "notificationList",
                notificationService.getNotifications(loginUser.getId())
        );

        return "notification/list";
    }

    @PostMapping("/notifications/{id}/read")
    public String read(@PathVariable Long id,
                       HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        notificationService.markAsRead(id, loginUser.getId());

        return "redirect:/notifications";
    }

    @PostMapping("/notifications/read-all")
    public String readAll(HttpSession session) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        notificationService.markAllAsRead(loginUser.getId());

        return "redirect:/notifications";
    }
}
