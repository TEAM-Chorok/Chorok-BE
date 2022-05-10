package com.finalproject.chorok.todo.dto;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class TodoAutoDto {
    private String workType;
    private LocalDate lastWorkTime;
    private LocalDate todoTime;
    private boolean status;
    private MyPlant myPlant;
    private User user;
}
