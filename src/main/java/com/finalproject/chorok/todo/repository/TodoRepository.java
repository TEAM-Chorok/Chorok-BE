package com.finalproject.chorok.todo.repository;


import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.todo.dto.TodoOnlyResponseDto;
import com.finalproject.chorok.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo findFirstByWorkTypeAndMyPlantAndUserOrderByLastWorkTimeDesc(String workType, MyPlant myPlant, User user);
    Optional<Todo> findByUserAndMyPlant_MyPlantNoAndWorkType(User user, Long myPlantNo, String workType);
    List<Todo> findAllByUser(User user);


    List<Todo> findAllByUserAndTodoTime(User user, LocalDate toDoTime);

    List<Todo> findFirstByUserAndTodoTime(User user, LocalDate minusDays);
Todo findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByLastWorkTimeDesc(User user, MyPlant myPlant, boolean status, String workType);
    List<TodoOnlyResponseDto> findFirstByUserAndTodoTimeAndMyPlant_MyPlantNo(User user, LocalDate toDoDate, Long myPlantNo);

    List<Todo> findByTodoTime(LocalDate toDoTime);

   Todo findByUserAndAndTodoNo(User user, Long todoNo);
}
