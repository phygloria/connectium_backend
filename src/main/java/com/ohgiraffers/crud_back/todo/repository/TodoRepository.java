package com.ohgiraffers.crud_back.todo.repository;

import com.ohgiraffers.crud_back.login.model.entity.User;
import com.ohgiraffers.crud_back.todo.model.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByUser(User user);
}
