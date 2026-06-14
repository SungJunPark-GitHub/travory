package com.travory.app.calendar.mapper;

import com.travory.app.calendar.dto.CalendarPostDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CalendarMapper {

    List<CalendarPostDto> findCalendarPostsByUserId(Long userId);
}
