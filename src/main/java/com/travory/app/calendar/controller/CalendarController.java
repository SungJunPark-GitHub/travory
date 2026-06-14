package com.travory.app.calendar.controller;

import com.travory.app.calendar.dto.CalendarPostDto;
import com.travory.app.calendar.service.CalendarService;
import com.travory.app.user.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;

    @GetMapping("/mypage/calendar")
    public String calendar(HttpSession session,
                           Model model) {

        UserDto loginUser =
                (UserDto) session.getAttribute("loginUser");

        if (loginUser == null) {
            return "redirect:/users/login";
        }

        List<CalendarPostDto> calendarPosts =
                calendarService.getCalendarPosts(loginUser.getId());

        Map<LocalDate, List<CalendarPostDto>> calendarMap =
                new TreeMap<>();

        for (CalendarPostDto post : calendarPosts) {
            LocalDate date = post.getStartDate();

            while (!date.isAfter(post.getEndDate())) {
                calendarMap.computeIfAbsent(date, key -> new java.util.ArrayList<>())
                        .add(post);
                date = date.plusDays(1);
            }
        }

        model.addAttribute("calendarPosts", calendarPosts);
        model.addAttribute("calendarMap", calendarMap);

        return "calendar/index";
    }
}
