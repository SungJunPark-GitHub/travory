package com.travory.app.itinerary.mapper;

import com.travory.app.itinerary.dto.ItineraryDayDto;
import com.travory.app.itinerary.dto.ItineraryItemDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ItineraryMapper {

    List<ItineraryDayDto> findDaysByPostId(Long postId);

    ItineraryDayDto findDayById(Long id);

    Integer findMaxDayNumber(Long postId);

    void insertDay(@Param("postId") Long postId,
                   @Param("dayNumber") Integer dayNumber);

    List<ItineraryItemDto> findItemsByDayId(Long dayId);

    void insertItem(@Param("dayId") Long dayId,
                    @Param("placeName") String placeName,
                    @Param("memo") String memo,
                    @Param("orderNumber") Integer orderNumber);

    void deleteItem(@Param("itemId") Long itemId,
                    @Param("postId") Long postId);
}
