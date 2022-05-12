package com.finalproject.chorok.todo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "random")
public class RandomMassage {
    @Id
    @Column(name = "message_no",unique = true,nullable = false)
    private int massageNo;
    @Column
    private String massage;
}
