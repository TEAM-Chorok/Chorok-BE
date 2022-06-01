package com.finalproject.chorok.common.utils;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.model.Todo2;
import com.finalproject.chorok.todo.repository.Todo2Repository;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@EnableScheduling // 아래의 클래스를 스케줄링 목적으로 사용하도록 하겠다는 명시
@Configuration
@RequiredArgsConstructor
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
public class ScheduledTodo3 {

    private final Todo2Repository todoRepository;
    private final MyPlantRepository myPlantRepository;

    //매일 00시 00분에 아래의 행위 반복
    @Scheduled(cron = "0 50 1 * * *")
    @SchedulerLock(name="SchedulerLock",lockAtMostFor = "PT30S", lockAtLeastFor = "PT30S")
    public void autoTodo() {
        String water = "물주기";
        String changing = "분갈이";
        String supplement = "영양제";
        String cleaning = "잎닦기";
        String windy = "환기";
        List<Todo2> waterTodos = todoRepository.findByWorkType(water);
        List<Todo2> changingTodos = todoRepository.findByWorkType(changing);
        List<Todo2> supplementTodos = todoRepository.findByWorkType(supplement);
        List<Todo2> cleaningTodos = todoRepository.findByWorkType(cleaning);
        List<Todo2> windyTodos = todoRepository.findByWorkType(windy);

        for (Todo2 todo : waterTodos) {
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            if (todo.getTodoTime().equals(LocalDate.now().minusDays(myPlantRepository.findByMyPlantNo(myPlant.getMyPlantNo()).getWatering()))) {
                String workType = todo.getWorkType();
                LocalDate lastWorkTime = todo.getTodoTime();
                LocalDate todoTime = LocalDate.now();
                boolean status = false;
                Todo2 todo1 = new Todo2(workType, lastWorkTime, todoTime, status, user, myPlant);
                if (!todoRepository.existsByTodoTimeAndWorkTypeAndMyPlant(LocalDate.now(), water, myPlant)){
                    System.out.println("워터링저장완");
                    todoRepository.save(todo1);
                }


            }
        }
        for (Todo2 todo : changingTodos) {
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            if (todo.getTodoTime().equals(LocalDate.now().minusDays(myPlantRepository.findByMyPlantNo(myPlant.getMyPlantNo()).getChanging()))) {
                String workType = todo.getWorkType();
                LocalDate lastWorkTime = todo.getTodoTime();
                LocalDate todoTime = LocalDate.now();
                boolean status = false;
                Todo2 todo1 = new Todo2(workType, lastWorkTime, todoTime, status, user, myPlant);
                if (!todoRepository.existsByTodoTimeAndWorkTypeAndMyPlant(LocalDate.now(), changing, myPlant)){
                    todoRepository.save(todo1);
                    System.out.println("분갈이저장완");
                }

            }
        }
        for (Todo2 todo : supplementTodos) {
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            if (todo.getTodoTime().equals(LocalDate.now().minusDays(myPlantRepository.findByMyPlantNo(myPlant.getMyPlantNo()).getSupplements()))) {
                String workType = todo.getWorkType();
                LocalDate lastWorkTime = todo.getTodoTime();
                LocalDate todoTime = LocalDate.now();
                boolean status = false;
                Todo2 todo1 = new Todo2(workType, lastWorkTime, todoTime, status, user, myPlant);
                if (!todoRepository.existsByTodoTimeAndWorkTypeAndMyPlant(LocalDate.now(), supplement, myPlant)){
                    todoRepository.save(todo1);
                    System.out.println("영양제저장완");
                }




            }
        }
        for (Todo2 todo : cleaningTodos) {
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            if (todo.getTodoTime().equals(LocalDate.now().minusDays(myPlantRepository.findByMyPlantNo(myPlant.getMyPlantNo()).getLeafCleaning()))) {
                String workType = todo.getWorkType();
                LocalDate lastWorkTime = todo.getTodoTime();
                LocalDate todoTime = LocalDate.now();
                boolean status = false;
                Todo2 todo1 = new Todo2(workType, lastWorkTime, todoTime, status, user, myPlant);
                if (!todoRepository.existsByTodoTimeAndWorkTypeAndMyPlant(LocalDate.now(), cleaning, myPlant)){
                    todoRepository.save(todo1);
                    System.out.println("잎닦기저장완");
                }



            }
        }
        for (Todo2 todo : windyTodos) {
            MyPlant myPlant = todo.getMyPlant();
            User user = todo.getUser();
            if (todo.getTodoTime().equals(LocalDate.now().minusDays(1))) {
                String workType = todo.getWorkType();
                LocalDate lastWorkTime = todo.getTodoTime();
                LocalDate todoTime = LocalDate.now();
                boolean status = false;
                Todo2 todo1 = new Todo2(workType, lastWorkTime, todoTime, status, user, myPlant);
                if (!todoRepository.existsByTodoTimeAndWorkTypeAndMyPlant(LocalDate.now(), windy, myPlant)){

                    todoRepository.save(todo1);
                    System.out.println("환기저장완");
                }


            }
        }


        System.out.println("투두자동저장완");


    }
}
