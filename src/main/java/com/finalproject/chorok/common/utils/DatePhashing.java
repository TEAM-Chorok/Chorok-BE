package com.finalproject.chorok.common.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatePhashing {
    public LocalDate getDate(String yearmonthday){
        String year = yearmonthday.substring(0, 4);
        String month = yearmonthday.substring(4, 6);
        String day = yearmonthday.substring(6);
        String date = year + "-" + month + "-" + day;
        LocalDate thatDay = LocalDate.parse(date);


        return thatDay;
    }
}
