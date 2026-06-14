package com.travory.app.post.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDto {

    private Long id;

    private Long userId;

    private String title;

    private String content;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer maxPeople;

    private Integer budget;

    private Integer viewCount;

    private String status;

    private String isPublic;

    private String imagePath;

    private LocalDateTime createdAt;
}
