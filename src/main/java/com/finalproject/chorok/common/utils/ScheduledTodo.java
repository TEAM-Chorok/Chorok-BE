package com.finalproject.chorok.common.utils;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@EnableScheduling // 아래의 클래스를 스케줄링 목적으로 사용하도록 하겠다는 명시
@Configuration
@RequiredArgsConstructor
public class ScheduledTodo {

    private final TodoRepository todoRepository;

    //매일 00시 30분에 아래의 행위 반복
    @Scheduled(cron = "0 0 0 * * *")
    public void autoTodo() {


        LocalDate waterToDoTime = LocalDate.now().minusDays(7);
        LocalDate changingToDoTime = LocalDate.now().minusDays(90);
        LocalDate supplementToDoTime = LocalDate.now().minusDays(90);
        LocalDate cleaningToDoTime = LocalDate.now().minusDays(3);
        LocalDate windyToDoTime = LocalDate.now().minusDays(1);
        LocalDate test = LocalDate.now();

        List<Todo> waterTodos = todoRepository.findByTodoTimeAndWorkType(waterToDoTime,"물주기");
        List<Todo> changingTodos = todoRepository.findByTodoTimeAndWorkType(changingToDoTime,"분갈이");
        List<Todo> supplementTodos = todoRepository.findByTodoTimeAndWorkType(supplementToDoTime,"영양제");
        List<Todo> cleaningTodos = todoRepository.findByTodoTimeAndWorkType(cleaningToDoTime,"잎닦기");
        List<Todo> windyTodos = todoRepository.findByTodoTimeAndWorkType(windyToDoTime,"환기");

        for (Todo todo : waterTodos) {
            String workType = todo.getWorkType();
            LocalDate lastWorkTime = todo.getTodoTime();
            LocalDate todoTime = LocalDate.now();
            boolean status = false;
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            Todo todo2 = new Todo(workType, lastWorkTime, todoTime, status, user, myPlant);

            todoRepository.save(todo2);
        }
        for (Todo todo : changingTodos) {
            String workType = todo.getWorkType();
            LocalDate lastWorkTime = todo.getTodoTime();
            LocalDate todoTime = LocalDate.now();
            boolean status = false;
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            Todo todo2 = new Todo(workType, lastWorkTime, todoTime, status, user, myPlant);

            todoRepository.save(todo2);
        }
        for (Todo todo : supplementTodos) {
            String workType = todo.getWorkType();
            LocalDate lastWorkTime = todo.getTodoTime();
            LocalDate todoTime = LocalDate.now();
            boolean status = false;
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            Todo todo2 = new Todo(workType, lastWorkTime, todoTime, status, user, myPlant);

            todoRepository.save(todo2);
        }
        for (Todo todo : cleaningTodos) {
            String workType = todo.getWorkType();
            LocalDate lastWorkTime = todo.getTodoTime();
            LocalDate todoTime = LocalDate.now();
            boolean status = false;
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            Todo todo2 = new Todo(workType, lastWorkTime, todoTime, status, user, myPlant);

            todoRepository.save(todo2);
        }
        for (Todo todo : windyTodos) {
            String workType = todo.getWorkType();
            LocalDate lastWorkTime = todo.getTodoTime();
            LocalDate todoTime = LocalDate.now();
            boolean status = false;
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            Todo todo2 = new Todo(workType, lastWorkTime, todoTime, status, user, myPlant);

            todoRepository.save(todo2);
        }
        System.out.println("투두자동저장완");


    }
}
