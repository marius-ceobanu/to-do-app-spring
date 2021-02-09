package com.codecool.todoapp.repository;

import com.codecool.todoapp.entity.DbUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DbUserRepository extends JpaRepository<DbUser, Long> {
    Optional<DbUser> findByUsername(String username);
}
