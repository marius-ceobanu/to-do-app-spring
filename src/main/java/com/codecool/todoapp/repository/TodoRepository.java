package com.codecool.todoapp.repository;

import com.codecool.todoapp.entity.Status;
import com.codecool.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    public List<Todo> getAllByStatus(Status status);
}
