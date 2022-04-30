package com.finalproject.chorok.todo.repository;

import com.sparta.realsample.Model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findAllByStatus();

}
