package com.finalproject.chorok.todo.repository;


import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.model.Todo2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface Todo2Repository extends JpaRepository<Todo2, Long> {

    Todo2 findFirstByWorkTypeAndMyPlantAndUserOrderByLastWorkTimeDesc(String workType, MyPlant myPlant, User user);

    Optional<Todo2> findByUserAndMyPlant_MyPlantNoAndWorkType(User user, Long myPlantNo, String workType);

    List<Todo2> findAllByUser(User user);

    List<Todo2> findAllByUserAndTodoTime(User user, LocalDate toDoTime);

    Optional<Todo2> findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByLastWorkTimeDesc(User user, MyPlant myPlant, boolean status, String workType);

    List<Todo2> findByTodoTimeAndWorkType(LocalDate toDoTime, String workType);

    Todo2 findByUserAndTodoNo(User user, Long todoNo);

    //달별 데이터 가져와보기

    List<Todo2> findAllByUserAndMyPlantAndStatusAndTodoTimeBetween(User user, MyPlant myPlant, boolean status, LocalDate start, LocalDate end );


    Todo2 findFirstByUserOrderByTodoNoAsc(User user);

    Todo2 findByUserAndTodoTimeAndWorkTypeAndMyPlant_MyPlantNo(User user, LocalDate todoTime, String workType, Long myPlantNo);
    Todo2 findByUserAndMyPlant_MyPlantNoAndWorkTypeOrderByLastWorkTimeDesc(User user, Long myPlantNo, String workType);
    Optional<Todo2> findFirstByUserAndMyPlant_MyPlantNoAndWorkTypeOrderByLastWorkTimeDesc(User user,Long myPlantNo,String workType);

    Todo2 findFirstByUserAndMyPlant_MyPlantNoOrderByTodoNoAsc(User user, Long myPlantNo);

    Optional<Todo2> findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByTodoTimeDesc(User user, MyPlant myPlant, boolean b, String workType);

    List<Todo2> findByWorkType(String workType);
    boolean existsByTodoTimeAndWorkTypeAndMyPlant(LocalDate todoTime, String workType, MyPlant myPlant);
}
