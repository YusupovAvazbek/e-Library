package com.example.elibrary.rest;

import com.example.elibrary.dto.LoginDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.dto.UsersDto;
import com.example.elibrary.service.UserService;
import com.example.elibrary.service.serviceImpl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@SecurityRequirement(name = "Authorization")
public class UserResources {
    private final UserService userService;
    private final UserServiceImpl service;

    @Operation(
            method = "get user by email",
            summary = "get user",
            description = "get active user by email",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User info",
            content = @Content(mediaType = "application/json"))
    )
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    public ResponseDto<UsersDto> getUser(@RequestParam String email){
        return userService.getUser(email);
    }
    @Operation(
            method = "Add new User",
            summary = "Add new User",
            description = "Need to send UsersDto to this end point to create new user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "send UsersDto",
                    content = @Content(mediaType = "application/json")
            )
    )
    @PostMapping
    public ResponseDto<UsersDto> addUser(@RequestBody UsersDto usersDto){
        return userService.addUser(usersDto);
    }
    @Operation(
            method = "edit user",
            summary = "edit user",
            description = "Need to send UsersDto to this end point to the user id and the field values you want to change"
    )
    @PatchMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseDto<UsersDto> editUser(@RequestBody UsersDto usersDto){
        return userService.editUser(usersDto);
    }
    @Operation(
            method = "delete user",
            summary = "delete user",
            description = "delete user by id"
    )
    @DeleteMapping
    @PreAuthorize("hasAnyAuthority('ROLE_USER','ROLE_ADMIN')")
    public ResponseDto<UsersDto> deleteUser(@RequestParam Integer id){
        return userService.delete(id);
    }

    @Operation(
            method = "login",
            summary = "get jwt token",
            description = "Need to send username and password"
    )
    @PostMapping("/login")
    public ResponseDto<String> login(@RequestBody LoginDto loginDto){
        Link link = Link.of("/book","get-all-books");
        ResponseDto<String> response = userService.login(loginDto);
        response.add(link);
        return response;
    }
    @GetMapping("/captcha")
    public ResponseDto<String> captcha(HttpServletResponse resp) throws IOException {
        return service.generateCaptcha(resp);
    }
}
