package com.travory.app.calendar.service;

import com.travory.app.calendar.dto.CalendarPostDto;
import com.travory.app.calendar.mapper.CalendarMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarServiceImpl implements CalendarService {

    private final CalendarMapper calendarMapper;

    @Override
    public List<CalendarPostDto> getCalendarPosts(Long userId) {
        return calendarMapper.findCalendarPostsByUserId(userId);
    }
}
