package com.finalproject.chorok.myPlant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyOnePlantResponseDto {
    private Long myPlantNo;
    private String myPlantImgUrl;
    private String myPlantPlace;
    private String myPlantName;
    private Long plantNo;
    private String plantName;


    public MyOnePlantResponseDto(Long myPlantNo, String myPlantImgUrl, String myPlantPlace, String myPlantName, Long plantNo,String plantName) {

        this.myPlantNo = myPlantNo;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantPlace = myPlantPlace;
        this.myPlantName = myPlantName;
        this.plantNo = plantNo;
        this.plantName = plantName;
    }
}
