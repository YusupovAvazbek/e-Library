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
public class Users {
    @Id
    @GeneratedValue(generator = "user_id")
    @SequenceGenerator(name = "user_id", sequenceName = "user_id_seq", allocationSize = 1)
    private Integer id;
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String passportId;
    private String password;
    private short isActive;
    private String role;
}
