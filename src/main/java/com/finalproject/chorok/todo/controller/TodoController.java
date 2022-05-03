package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.todo.dto.TodoResponseDto;
import com.finalproject.chorok.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;
//투두 만들기
    @PostMapping("/todo/{myPlantInfoId}")
    public void createTodo (@PathVariable Long myPlantInfoId, @RequestBody TodoRequestDto todoRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        todoService.createTodo(myPlantInfoId,todoRequestDto, userDetails);
    }

    //todo보기
    @GetMapping("/todo/{userId}")
    public void mytodo (@PathVariable Long userId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        todoService.getTodo(userId, userDetails);
    }
}
