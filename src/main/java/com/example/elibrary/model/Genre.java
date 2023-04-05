package com.example.elibrary.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Genre{
    @Id
    @GeneratedValue(generator = "category_id")
    @SequenceGenerator(name = "category_id", sequenceName = "category_id_seq")
    private Integer id;
    private String name;

}
