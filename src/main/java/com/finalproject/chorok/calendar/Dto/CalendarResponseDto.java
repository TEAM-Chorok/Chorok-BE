package com.finalproject.chorok.calendar.Dto;

import com.finalproject.chorok.todo.dto.BloomingDayResponstDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class CalendarResponseDto {
    List<CalendarTodoResponseDto> watering;
    List<CalendarTodoResponseDto> changing;
    List<CalendarTodoResponseDto> supplements;
    List<CalendarTodoResponseDto> leafCleaning;
    List<BloomingDayResponstDto> bloomingDays;
    public CalendarResponseDto( List<CalendarTodoResponseDto> watering,List<CalendarTodoResponseDto> changing, List<CalendarTodoResponseDto> supplements, List<CalendarTodoResponseDto> leafCleaning, List<BloomingDayResponstDto> bloomingDays){
        this.watering = watering;
        this.changing = changing;
        this.supplements = supplements;
        this.leafCleaning = leafCleaning;
        this.bloomingDays = bloomingDays;
    }
}
