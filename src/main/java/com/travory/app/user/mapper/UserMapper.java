package com.travory.app.user.mapper;

import com.travory.app.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUser(UserDto userDto);

    UserDto findByUserId(String userId);

    UserDto findById(Long id);

    UserDto findByEmail(String email);

    void updateProfile(UserDto userDto);

}
