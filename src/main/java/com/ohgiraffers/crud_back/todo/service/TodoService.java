package com.ohgiraffers.crud_back.todo.service;

import com.ohgiraffers.crud_back.login.model.entity.User;
import com.ohgiraffers.crud_back.login.repository.UserRepository;
import com.ohgiraffers.crud_back.todo.model.dto.TodoDTO;
import com.ohgiraffers.crud_back.todo.model.entity.Todo;
import com.ohgiraffers.crud_back.todo.repository.TodoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;  // UserRepository 추가

    @Autowired
    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<TodoDTO> getTodosByUser(User user) {
        return todoRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TodoDTO addTodo(TodoDTO todoDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Todo todo = new Todo();
        todo.setContent(todoDTO.getContent());
        todo.setDate(todoDTO.getDate());
        todo.setUser(user);

        Todo savedTodo = todoRepository.save(todo);
        return convertToDTO(savedTodo);
    }

    @Transactional
    public void deleteTodo(Long id, String username) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!todo.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You are not authorized to delete this todo");
        }

        todoRepository.delete(todo);
    }

    private TodoDTO convertToDTO(Todo todo) {
        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setContent(todo.getContent());
        dto.setDate(todo.getDate());
        return dto;
    }
}
