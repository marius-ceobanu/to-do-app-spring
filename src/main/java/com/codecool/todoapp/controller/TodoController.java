package com.codecool.todoapp.controller;

import com.codecool.todoapp.entity.Todo;
import com.codecool.todoapp.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/list")
    public List<Todo> listAll() {
        return todoService.getAllTodos();
    }
}
