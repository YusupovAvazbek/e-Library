package com.example.elibrary.service.mapper;

import com.example.elibrary.dto.UsersDto;
import com.example.elibrary.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class UserMapper implements CommonMapper<UsersDto, Users> {
    @Autowired
    protected PasswordEncoder passwordEncoder;
    @Override
    @Mapping(target = "isActive", expression = "java((short) 1)")
    @Mapping(target = "password",expression = "java(passwordEncoder.encode((dto.getPassword())))")
    @Mapping(target= "role",expression = "java(\"USER\")")
    abstract public Users toEntity(UsersDto dto);
}
