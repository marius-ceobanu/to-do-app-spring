package com.codecool.todoapp.service;

import com.codecool.todoapp.ToDoAppApplication;
import com.codecool.todoapp.entity.DbUser;
import com.codecool.todoapp.entity.Status;
import com.codecool.todoapp.entity.Todo;
import com.codecool.todoapp.repository.DbUserRepository;
import com.codecool.todoapp.repository.TodoRepository;
import org.assertj.core.util.Lists;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class TodoService {

    private static final Logger LOGGER = Logger.getLogger(ToDoAppApplication.class.getName());

    private final TodoRepository todoRepository;
    private final DbUserRepository dbUserRepository;

    private final PasswordEncoder passwordEncoder;

    public TodoService(TodoRepository todoRepository, DbUserRepository dbUserRepository) {
        this.todoRepository = todoRepository;
        this.dbUserRepository = dbUserRepository;

        this.passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();;
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public List<Todo> getAllByStatus(Status status) {
        return todoRepository.getAllByStatus(status);
    }

    public void addTodo(String title) {
        Todo todo = Todo.builder().title(title).status(Status.ACTIVE).build();
        todoRepository.save(todo);
    }

    public void editToDo(String id, String title) {
        todoRepository.findById(Long.parseLong(id))
                .ifPresent(
                    todo -> {todo.setTitle(title);
                            todoRepository.save(todo);}
                );
    }

    public String findById(String id) {
        Optional<Todo> todo =  todoRepository.findById(Long.parseLong(id));
        if(todo.isPresent()) {
            return todo.get().getTitle();
        } else {
            return "";
        }
    }

    public void deleteTodo(String id) {
        todoRepository.deleteById(Long.parseLong(id));
    }

    public void deleteAllCompleted(){
        todoRepository.findAll().forEach(todo -> {
            if(todo.getStatus().equals(Status.COMPLETE)) {
                todoRepository.delete(todo);
            }
        });
    }

    public void toggleCompleted(String id, Status status) {
        todoRepository.findById(Long.parseLong(id))
                .ifPresent(
                        todo -> {todo.setStatus(status);
                                todoRepository.save(todo);}
                );
    }

    public void toggleAllCompleted(Status status) {
        todoRepository.findAll().forEach(todo -> {
            todo.setStatus(status);
            todoRepository.save(todo);
        });
    }

    @PostConstruct
    public void afterInit() {
        Todo test1 = Todo.builder().title("first TODO Item").status(Status.COMPLETE).build();
        Todo test2 = Todo.builder().title("second TODO Item").status(Status.ACTIVE).build();
        Todo test3 = Todo.builder().title("third TODO Item").status(Status.COMPLETE).build();

        DbUser user = DbUser.builder()
                .username("user")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER"))
                .build();

        DbUser admin = DbUser.builder()
                .username("admin")
                .password(passwordEncoder.encode("password"))
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build();


        dbUserRepository.saveAll(Lists.newArrayList(user, admin));
        todoRepository.saveAll(Lists.newArrayList(test1, test2, test3));
        LOGGER.info(getAllTodos().toString());
        dbUserRepository.findAll().forEach(v -> LOGGER.info(" TodoAppUser :" + v.toString()));
    }
}
