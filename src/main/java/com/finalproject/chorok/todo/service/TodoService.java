package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.Login.repository.UserRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.todo.dto.TodoResponseDto;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.MyPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MyPlantRepository myPlantRepository;
    private final UserRepository userRepository;

    //투두 보여주기
    public List<TodoResponseDto> getTodo(Long userId, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        List<Todo> todos = todoRepository.findAll();
        List<TodoResponseDto> todoResponseDtos = new ArrayList<>();
        for (Todo todo : todos) {
            TodoResponseDto todoResponseDto = new TodoResponseDto(
                    todo.getMyPlant().getMyPlantInfoNo(),
                    todo.getMyPlant().getMyPlantName(),
                    todo.getMyPlant().getMyPlantImgUrl(),
                    todo.getMyPlant().getMyPlantPlace(),
//                    todoRepository.findAllByUserAndMyPlantAndWorkTypeOrderByWorkTypeWorkTypeDesc()
                    todoRepository.findAll()
            );
            todoResponseDtos.add(todoResponseDto);
        }
        return todoResponseDtos;
    }

    //투두만들기
    public void createTodo(Long myPlantInfoNo, TodoRequestDto todoRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        String workType = todoRequestDto.getWorkType();
        boolean status = todoRequestDto.isStatus();

        MyPlant myPlant = myPlantRepository.findById(myPlantInfoNo).orElseThrow(
                () -> new IllegalArgumentException("나의식물이 존재하지 않습니다.")
        );

        Todo todo = new Todo(user, workType, status, myPlant);
        todoRepository.save(todo);

    }

}
