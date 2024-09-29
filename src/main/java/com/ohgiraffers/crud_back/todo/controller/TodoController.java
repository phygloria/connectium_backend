package com.ohgiraffers.crud_back.todo.controller;

import com.ohgiraffers.crud_back.login.model.entity.User;
import com.ohgiraffers.crud_back.todo.model.dto.TodoDTO;
import com.ohgiraffers.crud_back.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<List<TodoDTO>> getTodos(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(todoService.getTodosByUser(user));
    }

    @PostMapping
    public ResponseEntity<TodoDTO> addTodo(@RequestBody TodoDTO todoDTO, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(todoService.addTodo(todoDTO, userDetails.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id, Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        todoService.deleteTodo(id, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }
}
