package com.finalproject.chorok.MyPlant.model;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.MyPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.todo.model.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    private int plantNo;
    private String myPlantPlace;
    private String myPlantImgUrl;
    private String myPlantName;
    private LocalDate startDay;
    private LocalDate endDay;

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

    }
    public MyPlant(int plantNo, String myPlantPlace, String myPlantImgUrl, String myPlantName, User user, LocalDate endDay, LocalDate startDay){
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

}
