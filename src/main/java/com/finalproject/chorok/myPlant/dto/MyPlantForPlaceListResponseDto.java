package com.finalproject.chorok.myPlant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MyPlantForPlaceListResponseDto {
    Long myPlantNo;
    Long plantNo;
    String myPlantPlace;
    String plantName;
    String myPlantImgUrl;
    String myPlantName;

    public MyPlantForPlaceListResponseDto(Long myPlantNo, Long plantNo, String myPlantPlace, String plantName, String myPlantImgUrl, String myPlantName){
        this.myPlantNo = myPlantNo;
        this.plantNo = plantNo;
        this.myPlantPlace = myPlantPlace;
        this.plantName = plantName;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantName = myPlantName;

    }
}
