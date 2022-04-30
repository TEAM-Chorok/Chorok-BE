package com.finalproject.chorok.todo.model;

import com.sparta.realsample.Dto.MyPlantRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
    private LocalDateTime startDay;
    private LocalDateTime endDay;
    private String myPlantPlace;
    private String myPlantImgUrl;
    private String myPlantName;
    @OneToMany
    @JoinColumn
    private List<Todo> todoList;

    @ManyToOne
    private User user;

    public MyPlant(MyPlantRequestDto myPlantRequestDto, List<Todo> todoList){
        this.plantNo = myPlantRequestDto.getPlantNo();
        this.myPlantPlace = myPlantRequestDto.getMyPlantPlaceCode();
        this.myPlantImgUrl = myPlantRequestDto.getMyPlantImgUrl();
        this.myPlantName = myPlantRequestDto.getMyPlantName();
        this.todoList = todoList;
        this.user = user;

    }
}
