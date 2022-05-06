package com.finalproject.chorok.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@NoArgsConstructor
public class TodoRequestDto {
    String workType;
    private boolean status;
}
