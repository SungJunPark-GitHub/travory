package com.travory.app.itinerary.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItineraryDayDto {

    private Long id;

    private Long postId;

    private Integer dayNumber;

    private LocalDateTime createdAt;

    private List<ItineraryItemDto> itemList;
}
