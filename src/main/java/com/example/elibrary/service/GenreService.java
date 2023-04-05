package com.example.elibrary.service;

import com.example.elibrary.dto.BookDto;
import com.example.elibrary.dto.GenreDto;
import com.example.elibrary.dto.ResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GenreService {
    ResponseDto<GenreDto> add(GenreDto genreDto);
    ResponseDto<GenreDto> edit(GenreDto genreDto);
    ResponseDto<List<GenreDto>> getAll();
    ResponseDto<GenreDto> getById(Integer id);
    ResponseDto<GenreDto> delete(Integer id);

}
