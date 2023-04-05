package com.example.elibrary.service.mapper;

import com.example.elibrary.dto.BookDto;
import com.example.elibrary.dto.UsersDto;
import com.example.elibrary.model.Book;
import com.example.elibrary.model.Users;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface  BookMapper extends CommonMapper<BookDto, Book>{
    @Mapping(target = "isAvailable", expression = "java((short) 1)")
    Book toEntity(BookDto dto);
}
