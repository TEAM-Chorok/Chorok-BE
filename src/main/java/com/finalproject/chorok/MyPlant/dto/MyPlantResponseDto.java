package com.finalproject.chorok.MyPlant.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.chorok.todo.model.Todo;
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
    private int plantNo;
    private String myPlantPlace;
    private String myPlantImgUrl;
    private String myPlantName;
//    private MyPlantPlaceCode myPlantPlaceCode;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDay;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDay;

    private List<Todo> todos;

public MyPlantResponseDto (Long MyPlantNo, int plantNo, String myPlantPlace, String myPlantImgUrl, String myPlantName, LocalDate startDay, LocalDate endDay, List<Todo> todos){
    this.myPlantNo = MyPlantNo;
    this.plantNo = plantNo;
    this.myPlantPlace = myPlantPlace;
    this.myPlantImgUrl = myPlantImgUrl;
    this.myPlantName = myPlantName;
    this.startDay = startDay;
    this.endDay = endDay;
    this.todos = todos;
}
}
