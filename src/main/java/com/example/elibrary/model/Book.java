package com.example.elibrary.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(generator = "book_id")
    @SequenceGenerator(name = "book_id", sequenceName = "book_id_seq", allocationSize = 1)
    private Integer id;
    private String title;
    private Integer price;
    private Integer amount;
    private String description;
    @OneToOne
    private Genre genre;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private BookDetail bookDetail;
    private Integer fileId;
    private short isAvailable;


}
