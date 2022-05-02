package com.finalproject.chorok.todo.model;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Todo{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long TodoNo;
    private String workType;
    private boolean status;
    @ManyToOne
    private MyPlant myPlant;

    @ManyToOne
    private User user;

    public Todo (User user, String workType, boolean status, MyPlant myPlant) {
        this.user = user;
        this.workType = workType;
        this.status = status;
        this.myPlant = myPlant;
    }

}
