package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoOnlyResponseDto;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.todo.dto.TodoResponseDto;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.MyPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MyPlantRepository myPlantRepository;

//todoOnlyResponseDto에 값 넣어주기

    //나의 모든 식물에 대한 투두 보여주기
    public List<TodoResponseDto> getTodo(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Todo> todos2 = todoRepository.findAll();
        List<Todo> todos = todoRepository.findFirstByUserAndTodoTime(user, LocalDate.now().minusDays(7)).stream().distinct().collect(Collectors.toList());
        List<TodoOnlyResponseDto> todoOnlyResponseDtos = new ArrayList<>();
        for (Todo todo : todos) {
            TodoOnlyResponseDto todoOnlyResponseDto = new TodoOnlyResponseDto(
                    todo.getTodoNo(),
                    todo.getMyPlant().getMyPlantNo(),
                    todo.getWorkType(),
                    todo.getLastWorkTime(),
                    todo.getTodoTime(),
                    todo.isStatus()
            );
            todoOnlyResponseDtos.add(todoOnlyResponseDto);
        }
        List<TodoResponseDto> todoResponseDtos = new ArrayList<>();
        //todoresponseDto에 하나씩 넣기
        for (Todo todo : todos) {
            TodoResponseDto todoResponseDto = new TodoResponseDto(
                    todo.getMyPlant().getMyPlantNo(),
                    todo.getMyPlant().getMyPlantName(),
                    todo.getMyPlant().getMyPlantImgUrl(),
                    todo.getMyPlant().getMyPlantPlace(),
                    //7에 식물별 워터사이클을 넣어주기. 오늘에서 워터사이클 빼준날짜가 마지막 투두타임일때, 찾아오는 로직.
                    todoOnlyResponseDtos);
            todoResponseDtos.add(todoResponseDto);
//            위에서 뽑은 list를 기반으로 false데이터 저장.
//            if (todoRepository.findAllByUserAndMyPlant_MyPlantNoAndTodoTime(user, todo.getMyPlant().getMyPlantNo(), LocalDate.now().minusDays(7)).size()>0){
//                for (int i = 0;i<=todoRepository.findAllByUserAndMyPlant_MyPlantNoAndTodoTime(user, todo.getMyPlant().getMyPlantNo(), LocalDate.now().minusDays(7)).size();i++) {
//                    todoRepository.save(new Todo(todoResponseDto.getTodos().get(i).getWorkType(),LocalDate.now().minusDays(7),LocalDate.now(),false,user, todo.getMyPlant()));
//                }
//            }
        }


        return todoResponseDtos;
    }

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
