package com.example.elibrary.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDetail {
    @Id
    @GeneratedValue(generator = "book_detail_seq")
    @SequenceGenerator(name = "book_detail_seq", sequenceName = "book_det_seq", allocationSize = 1)
    private Integer id;
    private String ISBN;
    private String author;
    private String cover;
    private String language;
    private Integer pages;
    private String publisher;
    private Date releaceDate;
}
