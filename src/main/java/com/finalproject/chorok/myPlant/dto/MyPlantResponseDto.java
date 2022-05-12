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
public class MyPlantResponseDto {
    private Long myPlantNo;
    private Long plantNo;
    private String plantName;
    private String myPlantPlace;
    private String myPlantImgUrl;
    private String myPlantName;
//    private MyPlantPlaceCode myPlantPlaceCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDay;

    private List<TodoOnlyResponseDto> todos;

public MyPlantResponseDto (Long myPlantNo, Long plantNo, String plantName, String myPlantPlace, String myPlantImgUrl, String myPlantName, LocalDate startDay, LocalDate endDay, List<TodoOnlyResponseDto> todos){
    this.myPlantNo = myPlantNo;
    this.plantNo = plantNo;
    this.plantName = plantName;
    this.myPlantPlace = myPlantPlace;
    this.myPlantImgUrl = myPlantImgUrl;
    this.myPlantName = myPlantName;
    this.startDay = startDay;
    this.endDay = endDay;
    this.todos = todos;
}
}
