package com.travory.app.itinerary.service;

import com.travory.app.itinerary.dto.ItineraryDayDto;
import com.travory.app.itinerary.mapper.ItineraryMapper;
import com.travory.app.post.dto.PostDto;
import com.travory.app.post.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItineraryServiceImpl implements ItineraryService {

    private final ItineraryMapper itineraryMapper;
    private final PostMapper postMapper;

    @Override
    public List<ItineraryDayDto> getItinerary(Long postId) {
        List<ItineraryDayDto> dayList =
                itineraryMapper.findDaysByPostId(postId);

        for (ItineraryDayDto day : dayList) {
            day.setItemList(
                    itineraryMapper.findItemsByDayId(day.getId())
            );
        }

        return dayList;
    }

    @Override
    public void createDay(Long postId, Long userId) {
        if (!isPostOwner(postId, userId)) {
            return;
        }

        Integer maxDayNumber =
                itineraryMapper.findMaxDayNumber(postId);

        int nextDayNumber =
                maxDayNumber == null ? 1 : maxDayNumber + 1;

        itineraryMapper.insertDay(postId, nextDayNumber);
    }

    @Override
    public void createItem(Long postId,
                           Long userId,
                           Long dayId,
                           String placeName,
                           String memo,
                           Integer orderNumber) {

        if (!isPostOwner(postId, userId)) {
            return;
        }

        ItineraryDayDto day =
                itineraryMapper.findDayById(dayId);

        if (day == null || !day.getPostId().equals(postId)) {
            return;
        }

        itineraryMapper.insertItem(dayId, placeName, memo, orderNumber);
    }

    @Override
    public void deleteItem(Long postId, Long userId, Long itemId) {
        if (!isPostOwner(postId, userId)) {
            return;
        }

        itineraryMapper.deleteItem(itemId, postId);
    }

    @Override
    public boolean isPostOwner(Long postId, Long userId) {
        PostDto post =
                postMapper.findById(postId);

        return post != null && post.getUserId().equals(userId);
    }
}
