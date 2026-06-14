package com.travory.app.user.service;

import com.travory.app.user.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

    void register(UserDto userDto);

    UserDto login(UserDto userDto);

    UserDto getUserById(Long id);

    void updateProfile(UserDto userDto);

    void updateProfile(UserDto userDto, MultipartFile profileImageFile);
}
