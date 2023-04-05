package com.example.elibrary.repository;

import com.example.elibrary.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmailAndIsActive(String email, short isActive);
    Optional<Users> findFirstByIdAndIsActive(Integer id, short isActive);
    Optional<Users> findFirstByEmail(String username);
}
