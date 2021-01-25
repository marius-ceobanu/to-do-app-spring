package com.codecool.todoapp.controller;

import com.codecool.todoapp.entity.Todo;
import com.codecool.todoapp.service.TodoService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.AllArgsConstructor;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TodoController {
    private final TodoService todoService;
    private static final String SUCCESS = "{\"success\":true}";

    @PostMapping("/list")
    public List<Todo> listAll() {
        return todoService.getAllTodos();
    }

    @PostMapping("/addTodo")
    @ResponseBody
    public String addTodo(@RequestBody JSObject title) {
//        todoService.addTodo(title);
        System.out.print(title);
        return SUCCESS;
    }
}
