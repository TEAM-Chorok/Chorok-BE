package com.finalproject.chorok.todo.model;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name = "blooming_day")
public class BloomingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bloomingDayNo;

    private LocalDate bloomingDay;

    @ManyToOne
    @JoinColumn(name = "my_plant_no",referencedColumnName = "my_plant_no")
    private MyPlant myPlant;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    public BloomingDay(LocalDate bloomingDay,MyPlant myPlant, User user){
        this.bloomingDay = bloomingDay;
        this.myPlant = myPlant;
        this.user = user;
    }
}
