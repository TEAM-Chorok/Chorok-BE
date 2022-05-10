package com.finalproject.chorok.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@RequiredArgsConstructor
public class BloomingDayResponstDto {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate bloomingDay;

    public BloomingDayResponstDto(LocalDate bloomingDay){
        this.bloomingDay = bloomingDay;
    }
}
