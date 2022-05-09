package com.finalproject.chorok.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TodoOnlyResponseDto {
    Long TodoNo;
    Long myPlantNo;
    String workType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate lastWorkTime;

    int days;

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
//    LocalDate toDoTime;

    private boolean status;

    public TodoOnlyResponseDto(Long TodoNo, Long myPlantNo, String workType, LocalDate lastWorkTime, int days, boolean status){
        this.TodoNo = TodoNo;
        this.myPlantNo = myPlantNo;
        this.workType = workType;
        this.days = days;
        this.lastWorkTime = lastWorkTime;
//        this.toDoTime = toDoTime;
        this.status = status;
    }


}
