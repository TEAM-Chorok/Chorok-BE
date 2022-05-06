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
    String workType;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate lastWorkTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate toDoTime;

    private boolean status;

    public TodoOnlyResponseDto(Long TodoNo, String workType, LocalDate lastWorkTime, LocalDate toDoTime,boolean status){
        this.TodoNo = TodoNo;
        this.workType = workType;
        this.lastWorkTime = lastWorkTime;
        this.toDoTime = toDoTime;
        this.status = status;
    }


}
