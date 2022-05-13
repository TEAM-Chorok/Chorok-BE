package com.finalproject.chorok.myPlant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class MyPlantForPlaceResponseDtoTest {
    List<MyPlantForPlaceListResponseDto> pp01;
    List<MyPlantForPlaceListResponseDto> pp02;
    List<MyPlantForPlaceListResponseDto> pp03;
    List<MyPlantForPlaceListResponseDto> pp04;
    List<MyPlantForPlaceListResponseDto> pp05;
    List<MyPlantForPlaceListResponseDto> pp06;
    public MyPlantForPlaceResponseDtoTest(
            List<MyPlantForPlaceListResponseDto> pp01,
    List<MyPlantForPlaceListResponseDto> pp02,
    List<MyPlantForPlaceListResponseDto> pp03,
    List<MyPlantForPlaceListResponseDto> pp04,
    List<MyPlantForPlaceListResponseDto> pp05,
    List<MyPlantForPlaceListResponseDto> pp06
    ){
        this.pp01 = pp01;
        this.pp02 = pp02;
        this.pp03 = pp03;
        this.pp04 = pp04;
        this.pp05 = pp05;
        this.pp06 = pp06;
    }
}
