package com.finalproject.chorok.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MypageMyplantFinalDto {
    int myPlantCount;
    List<MypageMyplantSixDto> mypageMyplantSixDtos;

    public MypageMyplantFinalDto(int myPlantCount, List<MypageMyplantSixDto> mypageMyplantSixDtos){
        this.myPlantCount = myPlantCount;
        this.mypageMyplantSixDtos = mypageMyplantSixDtos;
    }
}
