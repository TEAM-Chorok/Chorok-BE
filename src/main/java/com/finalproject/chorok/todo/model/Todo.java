package com.finalproject.chorok.todo.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class Todo extends Timestamped {

    @Id
    private Long TodoNo;
    private String workType;
    private boolean status;
    @ManyToOne
    private MyPlant myPlant;

}
