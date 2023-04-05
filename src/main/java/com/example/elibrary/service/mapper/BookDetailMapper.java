package com.example.elibrary.service.mapper;

import com.example.elibrary.dto.BookDetailDto;
import com.example.elibrary.model.BookDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookDetailMapper extends CommonMapper<BookDetailDto, BookDetail>{
}
