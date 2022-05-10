package com.finalproject.chorok.todo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BloomingDayRequestDto {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bloomingDay;
}
