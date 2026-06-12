package com.travory.app.user.service;

import com.travory.app.user.dto.UserDto;

public interface UserService {

    void register(UserDto userDto);

    UserDto login(UserDto userDto);
}