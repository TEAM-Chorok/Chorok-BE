package com.finalproject.chorok.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@RequiredArgsConstructor
public class MyPlantResponseDto {
    private Long MyPlantInfoNo;
    private String plantNo;
    private String myPlantImgUrl;
    private String myPlantName;
//    private MyPlantPlaceCode myPlantPlaceCode;




//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private String startDay;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private String endDay;

//public MyPlantRequestDto(String plantNo, String myPlantImgUrl, String myPlantName, MyPlantPlaceCode myPlantPlaceCode, LocalDateTime startDay, LocalDateTime endDay){
//    this.plantNo = plantNo;
//    this.myPlantImgUrl = myPlantImgUrl;
//    this.myPlantName = myPlantName;
//    this.myPlantPlaceCode = myPlantPlaceCode;
//    this.startDay = startDay;
//    this.endDay = endDay;
//
//}
}
