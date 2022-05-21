package com.finalproject.chorok.todo.repository;


import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
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

    Optional<Todo> findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByLastWorkTimeDesc(User user, MyPlant myPlant, boolean status, String workType);

    List<Todo> findByTodoTimeAndWorkType(LocalDate toDoTime, String workType);

    Todo findByUserAndTodoNo(User user, Long todoNo);

    //달별 데이터 가져와보기

    List<Todo> findAllByUserAndMyPlantAndStatusAndTodoTimeBetween(User user, MyPlant myPlant, boolean status, LocalDate start, LocalDate end );


    Todo findFirstByUserOrderByTodoNoAsc(User user);

    Todo findByUserAndTodoTimeAndWorkTypeAndMyPlant_MyPlantNo(User user, LocalDate todoTime, String workType, Long myPlantNo);
    Todo findByUserAndMyPlant_MyPlantNoAndWorkTypeOrderByLastWorkTimeDesc(User user, Long myPlantNo, String workType);
    Optional<Todo> findFirstByUserAndMyPlant_MyPlantNoAndWorkTypeOrderByLastWorkTimeDesc(User user,Long myPlantNo,String workType);

}
