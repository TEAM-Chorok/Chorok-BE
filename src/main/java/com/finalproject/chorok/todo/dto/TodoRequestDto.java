package com.finalproject.chorok.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class TodoRequestDto {
    String workType;
    private boolean status;
}
