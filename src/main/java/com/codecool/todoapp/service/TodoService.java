package com.codecool.todoapp.service;

import com.codecool.todoapp.ToDoAppApplication;
import com.codecool.todoapp.entity.Status;
import com.codecool.todoapp.entity.Todo;
import com.codecool.todoapp.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.logging.Logger;

@AllArgsConstructor
@Service
public class TodoService {

    private static final Logger LOGGER = Logger.getLogger(ToDoAppApplication.class.getName());

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @PostConstruct
    public void afterInit() {
        Todo test1 = Todo.builder().title("first TODO Item").status(Status.COMPLETE).build();
        Todo test2 = Todo.builder().title("second TODO Item").status(Status.ACTIVE).build();
        Todo test3 = Todo.builder().title("third TODO Item").status(Status.COMPLETE).build();

        todoRepository.saveAll(Lists.newArrayList(test1, test2, test3));
        LOGGER.info(getAllTodos().toString());
    }
}
