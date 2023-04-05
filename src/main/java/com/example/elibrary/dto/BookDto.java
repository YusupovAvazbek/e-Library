package com.example.elibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer id;
    private Integer amount;
    private String title;
    private Integer price;
    private String description;
    private GenreDto genreDto;
    private BookDetailDto bookDetail;
    private Integer fileId;
    private short isAvailable;

}
