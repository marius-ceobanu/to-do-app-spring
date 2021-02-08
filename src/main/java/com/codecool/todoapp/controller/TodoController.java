package com.codecool.todoapp.controller;

import com.codecool.todoapp.entity.Status;
import com.codecool.todoapp.entity.Todo;
import com.codecool.todoapp.service.TodoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TodoController {
    private final TodoService todoService;
    private static final String SUCCESS = "{\"success\":true}";

    @PostMapping("/list")
    public List<Todo> listAll(@RequestParam("status") String status) {
        List<Todo> all;
        if(status.equals("active")) {
            all = todoService.getAllByStatus(Status.ACTIVE);
        } else if(status.equals("complete")) {
            all = todoService.getAllByStatus(Status.COMPLETE);
        } else {
            all = todoService.getAllTodos();
        }
        return all;
    }

    @PostMapping("/addTodo")
    @ResponseBody
    public String addTodo(@RequestParam("todo-title") String title) {
        todoService.addTodo(title);
        return SUCCESS;
    }

    @GetMapping("/todos/{id}")
    @ResponseBody
    public String findTodo(@PathVariable String id) {
        return todoService.findById(id);
    }

    @PutMapping("/todos/{id}")
    @ResponseBody
    public String updateById(@PathVariable String id, @RequestParam("todo-title") String title) {
        todoService.editToDo(id, title);
        return SUCCESS;
    }

    @DeleteMapping("/todos/{id}")
    @ResponseBody
    public String deleteById(@PathVariable String id) {
        todoService.deleteTodo(id);
        return SUCCESS;
    }

    @DeleteMapping("/todos/completed")
    @ResponseBody
    public String deleteAllCompleted() {
        todoService.deleteAllCompleted();
        return SUCCESS;
    }

    @PutMapping("/todos/{id}/toggle_status")
    @ResponseBody
    public String toggleCompleted(@PathVariable String id, @RequestParam("status") String current) {
        todoService.toggleCompleted(id, getStatus(current));
        return SUCCESS;
    }

    @PutMapping("/todos/toggle_all")
    @ResponseBody
    public String toggleAllCompleted(@RequestParam("toggle-all") String current) {
        todoService.toggleAllCompleted(getStatus(current));
        return SUCCESS;
    }

    private Status getStatus(String current) {
        boolean completed = current.equals("true");
        return completed ? Status.COMPLETE : Status.ACTIVE;
    }
}
