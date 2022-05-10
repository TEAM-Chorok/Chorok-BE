package com.finalproject.chorok.calendar.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
public class CalendarTodoResponseDto {
String workType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    LocalDate toDoTime;

    public CalendarTodoResponseDto (String workType, LocalDate toDoTime){
        this.workType = workType;
        this.toDoTime = toDoTime;
    }

}
