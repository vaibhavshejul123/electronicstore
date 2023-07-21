package com.bikkadit.electronic.store.service;

import com.bikkadit.electronic.store.dto.UserDto;

import java.util.List;

public interface ServiceI {

    //    create user
    UserDto createUser(UserDto userDto);

    //    update user
    UserDto updateUser(UserDto userDto, String id);

    //    delete user
    void deleteUser(String id);

    //    get all user
    List<UserDto> getAll();

    //    get single user by id
    UserDto getSingleUser(String id);

    //    get single user by email
    UserDto GetUserByEmail(String email);

    //    search user
    List<UserDto> searchUser(String keyword);
}
