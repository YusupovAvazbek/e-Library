package com.example.elibrary.service.serviceImpl;
import com.example.elibrary.dto.GenreDto;
import com.example.elibrary.dto.ResponseDto;
import com.example.elibrary.model.Genre;
import com.example.elibrary.repository.GenreRepository;
import com.example.elibrary.service.GenreService;
import com.example.elibrary.service.mapper.GenreMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.elibrary.service.validator.AppStatusCode.*;
import static com.example.elibrary.service.validator.AppStatusMessages.*;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    @Override
    public ResponseDto<GenreDto> add(GenreDto genreDto) {
        Optional<Genre> byName = genreRepository.findByName(genreDto.getName());
        if (!byName.isEmpty()) {
            return ResponseDto.<GenreDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(DUPLICATE_ERROR)
                    .build();
        }
        try {
            Genre genre = genreMapper.toEntity(genreDto);
            genreRepository.save(genre);
            return ResponseDto.<GenreDto>builder()
                    .code(OK_CODE)
                    .message(OK)
                    .success(true)
                    .data(genreMapper.toDto(genre))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<GenreDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<GenreDto> edit(GenreDto genreDto) {
        if (genreDto.getId() == null) {
            return ResponseDto.<GenreDto>builder()
                    .code(VALIDATION_ERROR_CODE)
                    .message(NULL_VALUE)
                    .build();
        }
        Optional<Genre> byId = genreRepository.findById(genreDto.getId());
        if (byId.isEmpty()) {
            return ResponseDto.<GenreDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }
        try {
            Genre genre = byId.get();
            if (genreDto.getName() != null) {
                genre.setName(genreDto.getName());
            }
            genreRepository.save(genre);
            return ResponseDto.<GenreDto>builder()
                    .message(OK)
                    .code(OK_CODE)
                    .success(true)
                    .data(genreMapper.toDto(genre))
                    .build();
        } catch (Exception e) {
            return ResponseDto.<GenreDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .success(false)
                    .build();
        }
    }

    @Override
    public ResponseDto<List<GenreDto>> getAll() {
        try {
            return ResponseDto.<List<GenreDto>>builder()
                    .code(OK_CODE)
                    .data(genreRepository.findAll().stream().map(g -> genreMapper.toDto(g)).toList())
                    .success(true)
                    .message(OK)
                    .build();
        } catch (Exception e) {
            return ResponseDto.<List<GenreDto>>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<GenreDto> getById(Integer id) {
        try {
            return genreRepository.findById(id)
                    .map(b -> ResponseDto.<GenreDto>builder()
                            .success(true)
                            .data(genreMapper.toDto(b))
                            .code(0)
                            .build())
                    .orElse(ResponseDto.<GenreDto>builder()
                            .message(NOT_FOUND)
                            .build());
        } catch (Exception e) {
            return ResponseDto.<GenreDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }

    @Override
    public ResponseDto<GenreDto> delete(Integer id) {
        Optional<Genre> genreOptional = genreRepository.findById(id);
        if (genreOptional.isEmpty()) {
            return ResponseDto.<GenreDto>builder()
                    .code(NOT_FOUND_ERROR_CODE)
                    .message(NOT_FOUND)
                    .build();
        }

        try {
            genreRepository.delete(genreOptional.get());
            return ResponseDto.<GenreDto>builder()
                    .success(true)
                    .data(genreMapper.toDto(genreOptional.get()))
                    .code(0)
                    .build();

        } catch (Exception e) {
            return ResponseDto.<GenreDto>builder()
                    .code(DATABASE_ERROR_CODE)
                    .message(DATABASE_ERROR + e.getMessage())
                    .build();
        }
    }
}



