package com.finalproject.chorok.todo.dto;

import com.sparta.realsample.Model.Todo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
public class MyPlantRequestDto {
    private int plantNo;
    private String myPlantPlaceCode;
    private String myPlantImgUrl;
    private String myPlantName;
    private User user;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDay;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDay;
    private List<Todo> todoList;

    public MyPlantRequestDto(int plantNo, String myPlantPlaceCode, String myPlantImgUrl, String myPlantName, User user, LocalDateTime startDay, LocalDateTime endDay) {
        this.plantNo = plantNo;
        this.myPlantPlaceCode = myPlantPlaceCode;
        this.myPlantImgUrl = myPlantImgUrl;
        this.myPlantName = myPlantName;
        this.user = user;
        this.startDay = startDay;
        this.endDay = endDay;
    }


}
