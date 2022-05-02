package com.finalproject.chorok.todo.repository;


import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
//    List<Todo> findAllByStatus();
    List<Todo> findAllByUserAndMyPlantAndWorkTypeOrderByWorkTypeWorkTypeDesc();

    List<Todo> findAllById(Long userId);
}
