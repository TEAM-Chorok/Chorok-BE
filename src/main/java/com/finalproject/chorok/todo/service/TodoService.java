package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.Login.repository.UserRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.todo.model.MyPlant;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.MyPlantRepository;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final MyPlantRepository myPlantRepository;
    private final UserRepository userRepository;

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
