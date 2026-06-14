package com.travory.app.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String userId;
    private String password;
    private String userName;
    private String gender;
    private String phoneNumber;
    private String profileImage;
    private String email;
    private String bio;
    private String travelStyle;
    private String favoriteCountry;
    private LocalDateTime createdAt;
}
