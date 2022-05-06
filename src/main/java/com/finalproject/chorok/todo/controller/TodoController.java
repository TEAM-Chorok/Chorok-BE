package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.todo.dto.TodoResponseDto;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;
//투두 만들기
    @PostMapping("/todo/{myPlantNo}")
    public Todo createTodo (@PathVariable Long myPlantNo, @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
       return todoService.createTodo(myPlantNo,todoRequestDto, userDetails);
    }

    //todo보기->나중에 캘린더에서도 써먹을 수 있을까?
    @GetMapping("/todo")
    public List<TodoResponseDto> mytodo (@AuthenticationPrincipal UserDetailsImpl userDetails){
        return todoService.getTodo(userDetails);
    }

    //todo완료체크
    @PostMapping("/todo/ok/{myPlantNo}")
    public Todo checkTodo (@PathVariable Long myPlantNo, @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return todoService.createTodo(myPlantNo,todoRequestDto, userDetails);
    }
}
