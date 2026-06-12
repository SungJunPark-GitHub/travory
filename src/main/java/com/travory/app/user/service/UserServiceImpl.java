package com.travory.app.user.service;

import com.travory.app.user.dto.UserDto;
import com.travory.app.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(UserDto userDto) {

        if (userMapper.findByUserId(userDto.getUserId()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }

        if (userMapper.findByEmail(userDto.getEmail()) != null) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        userDto.setPassword(
                passwordEncoder.encode(userDto.getPassword())
        );

        userMapper.insertUser(userDto);
    }

    @Override
    public UserDto login(UserDto userDto) {

        UserDto findUser =
                userMapper.findByUserId(userDto.getUserId());

        if (findUser == null) {
            return null;
        }

        boolean match =
                passwordEncoder.matches(
                        userDto.getPassword(),
                        findUser.getPassword()
                );

        if (!match) {
            return null;
        }

        return findUser;
    }


}