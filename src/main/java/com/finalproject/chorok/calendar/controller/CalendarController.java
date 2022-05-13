package com.finalproject.chorok.calendar.controller;

import com.finalproject.chorok.calendar.Dto.CalendarResponseDto;
import com.finalproject.chorok.calendar.service.CalendarService;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final TodoRepository todoRepository;
    private final MyPlantRepository myPlantRepository;


    @GetMapping("/calendar/{yearmonth}/{myPlantNo}")
    public CalendarResponseDto getMonthlyrecord(@PathVariable String yearmonth, @PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String year = yearmonth.substring(0, 4);
        String month = yearmonth.substring(4);
        String start0 = year + "-" + month + "-01";
        LocalDate start = LocalDate.parse(start0);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return calendarService.getMonthly(myPlantNo, start, end, userDetails);

    }

    @PatchMapping("/calendar/{yearmonthday}/{myPlantNo}/{workType}")
    public ResponseEntity<?> checkTodoOkInCalendar(@PathVariable String yearmonthday, @PathVariable String workType, @PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String year = yearmonthday.substring(0, 4);
        System.out.println("year = " + year);
        String month = yearmonthday.substring(4,6);
        System.out.println("month = " + month);
        String day = yearmonthday.substring(6);
        System.out.println("day = " + day);
        String date = year + "-"+month+"-"+day;
        LocalDate thatDay = LocalDate.parse(date);

        return ResponseEntity.status(HttpStatus.OK).body(calendarService.checkTodoInCalendar(thatDay,workType,myPlantNo, userDetails));

    }
}
