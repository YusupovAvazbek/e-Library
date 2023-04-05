package com.example.elibrary.repository;

import com.example.elibrary.model.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    Optional<Book> findByTitle(String title);
    Optional<Book> findByIdAndIsAvailable(Integer id, short isAvailable);

    @Query(value = "select * from book b where (b.genre_id, b.price) in (select b2.genre_id, max(b2.price) from book b2 group by b2.genre_id)",nativeQuery = true)
    List<Book> getExpensiveBooks();
}
