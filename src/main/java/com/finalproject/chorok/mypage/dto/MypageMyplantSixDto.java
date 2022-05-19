package com.finalproject.chorok.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MypageMyplantSixDto {
    String myPlantImgUrl;
    String myPlantName;
    String plantName;

    public MypageMyplantSixDto(
                               String myPlantImgUrl,
                               String myPlantName,
                               String plantName){
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantName = myPlantName;
        this.plantName = plantName;
    }
}
