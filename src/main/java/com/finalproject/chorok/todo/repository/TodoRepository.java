package com.finalproject.chorok.todo.repository;


import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    Todo findFirstByWorkTypeAndMyPlantAndUserOrderByLastWorkTimeDesc(String workType, MyPlant myPlant, User user);
    List<Todo> findAllByMyPlant_MyPlantNoOrderByWorkTypeAsc(Long myPlantNo);
    List<Todo> findAllByUserAndMyPlant_MyPlantNoAndTodoTime(User user, Long myPlantNo, LocalDate todoTime);
    List<Todo> findAllByUserAndTodoTime(User user, LocalDate todoTime);

    Optional<Todo> findByUserAndMyPlant_MyPlantNoAndWorkType(User user, Long myPlantNo, String workType);


    List<Todo> findFirstByUserAndTodoTime(User user, LocalDate minusDays);
}
