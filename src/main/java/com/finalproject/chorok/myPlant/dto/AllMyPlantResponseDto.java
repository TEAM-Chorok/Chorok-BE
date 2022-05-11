package com.finalproject.chorok.myPlant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.chorok.todo.dto.TodoOnlyResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class AllMyPlantResponseDto {
    private Long myPlantNo;
    private String myPlantName;
    private String myPlantImgUrl;
    private String myPlantPlace;
    private String plantName;


    public AllMyPlantResponseDto(Long MyPlantNo, String myPlantName, String myPlantImgUrl, String myPlantPlace, String plantName) {
        this.myPlantNo = MyPlantNo;
        this.myPlantPlace = myPlantPlace;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantName = myPlantName;
        this.plantName = plantName;

    }
}
