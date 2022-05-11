package com.finalproject.chorok.myPlant.model;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.myPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.myPlant.dto.MyPlantUpdateRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter // get 함수를 일괄적으로 만들어줍니다.
@Setter
@NoArgsConstructor // 기본 생성자를 만들어줍니다.
@Entity
@Table(name = "my_plant")
public class MyPlant {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "my_plant_no")
    private Long myPlantNo;
    private Long plantNo;
    private String myPlantPlace;
    private String myPlantImgUrl;
    private String myPlantName;
    private LocalDate startDay;
    private LocalDate endDay;
    private int watering;
    private int changing;
    private int supplements;
    private int leafCleaning;

//    @OneToMany(mappedBy = "myPlant",cascade =CascadeType.ALL)
//    private List<Todo> todoList;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    public MyPlant(MyPlantRequestDto myPlantRequestDto, User user){
        this.plantNo = myPlantRequestDto.getPlantNo();
        this.myPlantPlace = myPlantRequestDto.getMyPlantPlaceCode();
        this.myPlantImgUrl = myPlantRequestDto.getMyPlantImgUrl();
        this.myPlantName = myPlantRequestDto.getMyPlantName();
        this.startDay = myPlantRequestDto.getStartDay();
        this.endDay = myPlantRequestDto.getEndDay();
        this.user = user;
        this.watering = 7;
        this.changing = 90;
        this.supplements = 90;
        this.leafCleaning = 3;

    }
    public MyPlant(Long plantNo, String myPlantPlace, String myPlantImgUrl, String myPlantName, User user, LocalDate endDay, LocalDate startDay){
        this.plantNo = plantNo;
        this.myPlantPlace = myPlantPlace;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantName = myPlantName;
        this.user = user;
        this.startDay = startDay;
        this.endDay = endDay;


    }
    public MyPlant(MyPlantResponseDto myPlantResponseDto, User user){
        this.plantNo = myPlantResponseDto.getPlantNo();
        this.myPlantPlace = myPlantResponseDto.getMyPlantPlace();
        this.myPlantImgUrl = myPlantResponseDto.getMyPlantImgUrl();
        this.myPlantName = myPlantResponseDto.getMyPlantImgUrl();
        this.startDay = myPlantResponseDto.getStartDay();
        this.endDay = myPlantResponseDto.getEndDay();
        this.user = user;

    }

    public void update(MyPlantUpdateRequestDto myPlantUpdateRequestDto){
        this.plantNo = myPlantUpdateRequestDto.getPlantNo();
        this.myPlantName = myPlantUpdateRequestDto.getMyPlantName();
        this.myPlantPlace = myPlantUpdateRequestDto.getMyPlantPlace();
        this.myPlantImgUrl = myPlantUpdateRequestDto.getMyPlantImgUrl();
    }

}
