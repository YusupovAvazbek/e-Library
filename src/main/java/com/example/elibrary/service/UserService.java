package com.example.elibrary.service;

import com.example.elibrary.dto.LoginDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.dto.UsersDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    ResponseDto<UsersDto> addUser(UsersDto usersDto);
    ResponseDto<UsersDto> getUser(String email);
    ResponseDto<UsersDto> editUser(UsersDto usersDto);
    ResponseDto<UsersDto> delete(Integer id);

    ResponseDto<String> login(LoginDto loginDto);
}
