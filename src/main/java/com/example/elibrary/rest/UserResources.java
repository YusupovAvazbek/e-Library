package com.example.elibrary.rest;

import com.example.elibrary.dto.LoginDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.dto.UsersDto;
import com.example.elibrary.service.UserService;
import com.example.elibrary.service.serviceImpl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserResources {
    private final UserService userService;

    @GetMapping
    public ResponseDto<UsersDto> getUser(@RequestParam String email){
        return userService.getUser(email);
    }
    @PostMapping
    public ResponseDto<UsersDto> addUser(@RequestBody UsersDto usersDto){
        return userService.addUser(usersDto);
    }
    @PatchMapping
    public ResponseDto<UsersDto> editUser(@RequestBody UsersDto usersDto){
        return userService.editUser(usersDto);
    }
    @DeleteMapping
    public ResponseDto<UsersDto> deleteUser(@RequestParam Integer id){
        return userService.delete(id);
    }
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto){
        return userService.login(loginDto);
    }
}
