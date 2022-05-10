package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MyPlantRepository myPlantRepository;


    //투두만들기
    public Todo createTodo(Long myPlantNo, TodoRequestDto todoRequestDto, UserDetailsImpl userDetails) {

        User user = userDetails.getUser();
        Optional<Todo> firstTodo = todoRepository.findByUserAndMyPlant_MyPlantNoAndWorkType(user, myPlantNo, todoRequestDto.getWorkType());

        //없을 경우 투두 일단 만들어주고 시작
        if (!firstTodo.isPresent()) {
            MyPlant findMyPlant = myPlantRepository.findByUserAndMyPlantNo(user, myPlantNo);
            Todo autoTodo = firstTodo.orElseGet(
                    () -> todoRepository.save(new Todo(todoRequestDto.getWorkType(), LocalDate.now(), LocalDate.now(), true, user, findMyPlant)));

            return todoRepository.save(autoTodo);
        } else {
            //실제 투두만들기 시작
            String workType = todoRequestDto.getWorkType();

            MyPlant myPlant = myPlantRepository.findById(myPlantNo).orElseThrow(
                    () -> new IllegalArgumentException("나의식물이 존재하지 않습니다.")
            );

            LocalDate lastWorkTime = todoRepository.findFirstByWorkTypeAndMyPlantAndUserOrderByLastWorkTimeDesc(workType, myPlant, user).getTodoTime();


            LocalDate todoTime = LocalDate.now();
            Todo todo = new Todo(todoRequestDto, lastWorkTime, todoTime, userDetails.getUser(), myPlant);

            return todoRepository.save(todo);
        }

    }


    //
}
