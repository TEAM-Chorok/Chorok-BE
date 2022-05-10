package com.finalproject.chorok.MyPlant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class EndDayReqeustDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDay;
}
