package com.finalproject.chorok.calendar.Dto;

import com.finalproject.chorok.todo.dto.BloomingDayResponstDto;
import com.finalproject.chorok.todo.dto.SprayingDayResponstDto;
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
    List<CalendarTodoResponseDto> airRefreshing;
    List<BloomingDayResponstDto> bloomingDays;
    List<SprayingDayResponstDto> sprayingDays;

    public CalendarResponseDto(List<CalendarTodoResponseDto> watering,
                               List<CalendarTodoResponseDto> changing,
                               List<CalendarTodoResponseDto> supplements,
                               List<CalendarTodoResponseDto> leafCleaning,
                               List<CalendarTodoResponseDto> airRefreshing,
                               List<BloomingDayResponstDto> bloomingDays,
                               List<SprayingDayResponstDto> sprayingDays

    ) {
        this.watering = watering;
        this.changing = changing;
        this.supplements = supplements;
        this.leafCleaning = leafCleaning;
        this.airRefreshing = airRefreshing;
        this.bloomingDays = bloomingDays;
        this.sprayingDays = sprayingDays;
    }
}
