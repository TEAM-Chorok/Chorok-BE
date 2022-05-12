package com.finalproject.chorok.myPlant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class MyAllPlantDetailResponseDto {
    private Long myPlantNo;
    private String myPlantImgUrl;
    private String myPlantPlace;
    private String myPlantName;
    private String plantName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDay;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDay;

    public MyAllPlantDetailResponseDto (Long myPlantNo, String myPlantImgUrl, String myPlantPlace, String myPlantName, String plantName, LocalDate startDay, LocalDate endDay){
        this.myPlantNo = myPlantNo;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantPlace = myPlantPlace;
        this.myPlantName = myPlantName;
        this.plantName = plantName;
        this.startDay = startDay;
        this.endDay = endDay;
    }


}
