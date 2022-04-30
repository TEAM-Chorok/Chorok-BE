package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import com.finalproject.chorok.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TodoController {
    private final TodoService todoService;

    @PostMapping("/todo/{myPlantInfoId}")
    public void createTodo (@PathVariable Long myPlantInfoId, @RequestBody TodoRequestDto todoRequestDto, UserDetailsImpl userDetails){
        todoService.createTodo(myPlantInfoId,todoRequestDto, userDetails);
    }
}
