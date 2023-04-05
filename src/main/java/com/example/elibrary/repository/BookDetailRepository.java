package com.example.elibrary.repository;

import com.example.elibrary.model.BookDetail;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetail, Integer> {
}
