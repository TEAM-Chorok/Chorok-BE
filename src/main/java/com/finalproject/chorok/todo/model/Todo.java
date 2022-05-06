package com.finalproject.chorok.todo.model;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.todo.dto.TodoRequestDto;
import lombok.*;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TodoNo;
    private String workType;
    private LocalDate lastWorkTime;
    private LocalDate todoTime;
    private boolean status;

    @ManyToOne
    private MyPlant myPlant;

    @ManyToOne
    private User user;

    public Todo(String workType, LocalDate lastWorkTime, LocalDate todoTime, boolean status, User user, MyPlant myPlant) {
        this.workType = workType;
        this.lastWorkTime = lastWorkTime;
        this.todoTime = todoTime;
        this.status = status;
        this.user = user;
        this.myPlant = myPlant;
    }
//    public Todo (String workType, LocalDate lastWorkTime, boolean status, MyPlant myPlant, User user) {
//        this.user = user;
//        this.workType = workType;
//        this.lastWorkTime = lastWorkTime;
//        this.status = status;
//        this.myPlant = myPlant;
//    }

    public Todo(TodoRequestDto todoRequestDto, LocalDate lastWorkTime, LocalDate todoTime, User user, MyPlant myPlant) {
        this.workType = todoRequestDto.getWorkType();
        this.status = todoRequestDto.isStatus();
        this.user = user;
        this.lastWorkTime = lastWorkTime;
        this.todoTime = todoTime;
        this.myPlant = myPlant;

    }


}
