package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.myPlant.service.MyPlantService;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.TodoRepository;
import com.finalproject.chorok.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;
    private final MyPlantService myPlantService;
    private final TodoRepository todoRepository;
//투두 만들기
    @PostMapping("/todo/{myPlantNo}")
    public Todo createTodo (@PathVariable Long myPlantNo, @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
       return todoService.createTodo(myPlantNo,todoRequestDto, userDetails);
    }

    //todo보기->나중에 캘린더에서도 써먹을 수 있을까?
    @GetMapping("/todo")
    public List<MyPlantResponseDto> mytodo (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return myPlantService.getMyPlantForTodo(userDetails);
    }
//투두완료체크
    @PatchMapping("/todo/ok/{todoNo}")
    public void checkTodoOk(@PathVariable Long todoNo, @AuthenticationPrincipal UserDetailsImpl userDetails){
       User user = userDetails.getUser();
        try {
            Todo todo = todoRepository.findByUserAndTodoNo(user, todoNo);
            todo.setStatus(true);
            todoRepository.save(todo);
        }
        catch (Exception e){
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PatchMapping("/todo/cancle/{todoNo}")
    public void checkTodoCancle(@PathVariable Long todoNo, @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        try {
            Todo todo = todoRepository.findByUserAndTodoNo(user, todoNo);
            todo.setStatus(false);
            todoRepository.save(todo);
        }
        catch (Exception e){
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
