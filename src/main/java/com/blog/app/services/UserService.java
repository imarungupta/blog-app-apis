package com.blog.app.services;

import com.blog.app.payloads.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto );
    UserDto updateUser(UserDto userDto, Integer userDtoId );
    UserDto getUserById(Integer userDtoId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userDtoId);
}
