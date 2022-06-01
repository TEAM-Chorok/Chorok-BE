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
@Table(name = "todo")
public class Todo2 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoNo;
    private String workType;
    private LocalDate lastWorkTime;
    private LocalDate todoTime;
    private boolean status;

    @ManyToOne
    @JoinColumn(name = "my_plant_no", referencedColumnName = "my_plant_no")
    private MyPlant myPlant;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public Todo2(String workType, LocalDate lastWorkTime, LocalDate todoTime, boolean status) {
        this.workType = workType;
        this.lastWorkTime = lastWorkTime;
        this.todoTime = todoTime;
        this.status = status;
    }

    public Todo2(TodoRequestDto todoRequestDto, LocalDate lastWorkTime, LocalDate todoTime, User user, MyPlant myPlant) {
        this.workType = todoRequestDto.getWorkType();
        this.status = todoRequestDto.isStatus();
        this.user = user;
        this.lastWorkTime = lastWorkTime;
        this.todoTime = todoTime;
        this.myPlant = myPlant;

    }

    //자동저장투두
    public Todo2(TodoAutoDto todoAutoDto) {
        this.workType = todoAutoDto.getWorkType();
        this.lastWorkTime = todoAutoDto.getLastWorkTime();
        this.todoTime = todoAutoDto.getTodoTime();
        this.status = todoAutoDto.isStatus();
        this.myPlant = todoAutoDto.getMyPlant();
        this.user = todoAutoDto.getUser();
    }
}
