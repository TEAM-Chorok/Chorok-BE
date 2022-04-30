package com.finalproject.chorok.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TodoRequestDto {
    String workType;
    private boolean status;
}
