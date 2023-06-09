package com.example.elibrary.repository;

import com.example.elibrary.model.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FileRepository extends JpaRepository<File, Integer> {
}
