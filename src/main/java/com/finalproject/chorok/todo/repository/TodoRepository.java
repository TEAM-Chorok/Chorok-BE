package com.finalproject.chorok.todo.repository;


import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
//    List<Todo> findAllByStatus();
//    List<Todo> findAllByUserAndMyPlantAndWorkTypeOrderByWorkTypeWorkTypeDesc();

//    List<Todo> findAllById(Long userId);
}
