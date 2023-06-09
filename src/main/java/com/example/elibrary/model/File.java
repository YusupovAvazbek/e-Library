package com.example.elibrary.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class File {
    @Id
    @GeneratedValue(generator = "file_id_seq")
    @SequenceGenerator(name = "file_id_seq", sequenceName = "file_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String path;
    private String ext;
    private LocalDateTime createdAt;
}
