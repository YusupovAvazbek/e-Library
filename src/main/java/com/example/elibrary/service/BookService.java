package com.example.elibrary.service;

import com.example.elibrary.dto.BookDto;
import com.example.elibrary.dto.ResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BookService {

    ResponseDto<BookDto> add(BookDto bookDto, MultipartFile file);
    ResponseDto<BookDto> edit(BookDto bookDto);
    ResponseDto<BookDto> delete(Integer id);
    ResponseDto<Page<EntityModel<BookDto>>> getAllBooks(Integer page, Integer size);
    ResponseDto<BookDto> getById(Integer id);
    ResponseDto<Page<BookDto>> universalSearch(Map<String, String> params);
    ResponseDto<List<BookDto>> getExpensiveBooks();
}
