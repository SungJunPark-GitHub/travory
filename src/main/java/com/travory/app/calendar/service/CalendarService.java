package com.travory.app.calendar.service;

import com.travory.app.calendar.dto.CalendarPostDto;

import java.util.List;

public interface CalendarService {

    List<CalendarPostDto> getCalendarPosts(Long userId);
}
