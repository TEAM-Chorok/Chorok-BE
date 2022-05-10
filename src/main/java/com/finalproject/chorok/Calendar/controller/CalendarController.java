package com.finalproject.chorok.Calendar.controller;

import com.finalproject.chorok.Calendar.Dto.CalendarResponseDto;
import com.finalproject.chorok.Calendar.Dto.CalendarTodoResponseDto;
import com.finalproject.chorok.Calendar.service.CalendarService;
import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.MyPlant.repository.MyPlantRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final TodoRepository todoRepository;
    private final MyPlantRepository myPlantRepository;

//    @GetMapping("/calendar/{yearmonth}/{myPlantNo}")
//    public List<CalendarTodoResponseDto> getMonthlyrecord(@PathVariable String yearmonth, @PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        String year = yearmonth.substring(0,4);
//        String month = yearmonth.substring(4);
//        String start0 = year+"-"+month+"-01";
//        LocalDate start = LocalDate.parse(start0);
//        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
//return calendarService.getMonthly(myPlantNo,start,end,userDetails);
//
//    }
@GetMapping("/calendar/{yearmonth}/{myPlantNo}")
public CalendarResponseDto getMonthlyrecord(@PathVariable String yearmonth, @PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
    String year = yearmonth.substring(0,4);
    String month = yearmonth.substring(4);
    String start0 = year+"-"+month+"-01";
    LocalDate start = LocalDate.parse(start0);
    LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
    return calendarService.getMonthly2(myPlantNo,start,end,userDetails);

}
}
