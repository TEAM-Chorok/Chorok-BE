package com.finalproject.chorok.todo.model;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.todo.dto.MyPlantRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@Setter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity
public class MyPlant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long MyPlantInfoNo;
    private int plantNo;
    private String myPlantPlace;
    private String myPlantImgUrl;
    private String myPlantName;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
    @OneToMany
    @JoinColumn
    private List<Todo> todoList;

    @ManyToOne
    private User user;

    public MyPlant(MyPlantRequestDto myPlantRequestDto, User user){
        this.plantNo = myPlantRequestDto.getPlantNo();
        this.myPlantPlace = myPlantRequestDto.getMyPlantPlaceCode();
        this.myPlantImgUrl = myPlantRequestDto.getMyPlantImgUrl();
        this.myPlantName = myPlantRequestDto.getMyPlantName();
        this.user = user;

    }
    public MyPlant(int plantNo, String myPlantPlace, String myPlantImgUrl, String myPlantName, LocalDate startDay, LocalDate endDay, User user){
        this.plantNo = plantNo;
        this.myPlantPlace = myPlantPlace;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantName = myPlantName;
        this.user = user;

    }

}
