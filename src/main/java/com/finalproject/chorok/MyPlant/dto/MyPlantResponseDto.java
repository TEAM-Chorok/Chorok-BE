package com.finalproject.chorok.MyPlant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.todo.model.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class MyPlantResponseDto {
    private Long MyPlantInfoNo;
    private int plantNo;
    private String myPlantPlace;
    private String myPlantImgUrl;
    private String myPlantName;
//    private MyPlantPlaceCode myPlantPlaceCode;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private String startDay;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private String endDay;

public MyPlantResponseDto (Long MyPlantInfoNo, int plantNo, String myPlantPlace, String myPlantImgUrl, String myPlantName, String startDay, String endDay){
    this.MyPlantInfoNo = MyPlantInfoNo;
    this.plantNo = plantNo;
    this.myPlantPlace = myPlantPlace;
    this.myPlantImgUrl = myPlantImgUrl;
    this.myPlantName = myPlantName;
    this.startDay = startDay;
    this.endDay = endDay;
}
}
