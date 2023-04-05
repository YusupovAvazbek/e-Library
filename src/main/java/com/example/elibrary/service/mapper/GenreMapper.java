package com.example.elibrary.service.mapper;

import com.example.elibrary.dto.GenreDto;
import com.example.elibrary.model.Genre;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper extends CommonMapper<GenreDto, Genre>{
}
