package com.example.elibrary.repository;

import com.example.elibrary.model.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIdAndIsAvailable(Integer id, short isAvailable);
}
