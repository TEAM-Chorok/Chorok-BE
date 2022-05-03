package com.finalproject.chorok.MyPlant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Setter
@Getter
@NoArgsConstructor
public class MyPlantRequestDto {
    private int plantNo;
    private String myPlantPlaceCode;
    private String myPlantImgUrl;
    private String myPlantName;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String startDay;
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String endDay;



}
