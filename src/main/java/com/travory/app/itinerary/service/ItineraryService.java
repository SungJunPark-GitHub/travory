package com.travory.app.itinerary.service;

import com.travory.app.itinerary.dto.ItineraryDayDto;

import java.util.List;

public interface ItineraryService {

    List<ItineraryDayDto> getItinerary(Long postId);

    void createDay(Long postId, Long userId);

    void createItem(Long postId,
                    Long userId,
                    Long dayId,
                    String placeName,
                    String memo,
                    Integer orderNumber);

    void deleteItem(Long postId, Long userId, Long itemId);

    boolean isPostOwner(Long postId, Long userId);
}
