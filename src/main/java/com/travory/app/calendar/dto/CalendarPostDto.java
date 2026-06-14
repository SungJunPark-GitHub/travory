package com.travory.app.calendar.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarPostDto {

    private Long postId;

    private String title;

    private LocalDate startDate;

    private LocalDate endDate;

    private String status;

    private String role;
}
