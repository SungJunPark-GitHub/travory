package com.travory.app.itinerary.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItineraryItemDto {

    private Long id;

    private Long dayId;

    private String placeName;

    private String memo;

    private Integer orderNumber;

    private LocalDateTime createdAt;
}
