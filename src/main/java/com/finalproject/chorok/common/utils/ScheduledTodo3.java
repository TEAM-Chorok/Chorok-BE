package com.finalproject.chorok.common.utils;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.model.Todo2;
import com.finalproject.chorok.todo.repository.Todo2Repository;
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
    @Scheduled(cron = "0 52 2 * * *")
    @SchedulerLock(name="SchedulerLock",lockAtMostFor = "PT30S", lockAtLeastFor = "PT30S")
    public void autoTodo() {
        String water = "물주기";
        String changing = "분갈이";
        String supplement = "영양제";
        String cleaning = "잎닦기";

        for (int i=0; i<20; i++) {

                Todo2 todo1 = new Todo2("물주기", LocalDate.now(), LocalDate.now(), true);

                    todoRepository.save(todo1);
                }


            }

}
