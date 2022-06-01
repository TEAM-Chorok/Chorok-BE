package com.finalproject.chorok.todo.model;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.todo.dto.TodoAutoDto;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Todo3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoNo;
    private String workType;
    private LocalDate lastWorkTime;
    private LocalDate todoTime;
    private boolean status;


    public Todo3(String workType, LocalDate lastWorkTime, LocalDate todoTime, boolean status) {
        this.workType = workType;
        this.lastWorkTime = lastWorkTime;
        this.todoTime = todoTime;
        this.status = status;
    }


    }



