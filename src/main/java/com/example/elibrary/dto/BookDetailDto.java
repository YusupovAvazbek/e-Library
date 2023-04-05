package com.example.elibrary.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetailDto {
    private Integer id;
    private String ISBN;
    private String author;
    private String cover;
    private String language;
    private Integer pages;
    private String publisher;
    private Date releaceDate;
}
