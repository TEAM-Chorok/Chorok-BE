package com.finalproject.chorok.calendar.controller;

import com.finalproject.chorok.calendar.Dto.CalendarResponseDto;
import com.finalproject.chorok.calendar.service.CalendarService;
import com.finalproject.chorok.common.utils.DatePhashing;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.SprayingDayResponstDto;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CalendarController {

    private final CalendarService calendarService;
    private final TodoRepository todoRepository;
    private final DatePhashing datePhashing;


    @GetMapping("/calendar/{yearmonth}/{myPlantNo}")
    public ResponseEntity<CalendarResponseDto> getMonthlyrecord(@PathVariable String yearmonth, @PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String year = yearmonth.substring(0, 4);
        String month = yearmonth.substring(4);
        String start0 = year + "-" + month + "-01";
        LocalDate start = LocalDate.parse(start0);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.getMonthly(myPlantNo, start, end, userDetails));

    }

    @PatchMapping("/calendar/{yearmonthday}/{myPlantNo}/{workType}")
    public ResponseEntity<?> checkTodoOkInCalendar(@PathVariable String yearmonthday, @PathVariable String workType, @PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        LocalDate thatDay = datePhashing.getDate(yearmonthday);
        return ResponseEntity.status(HttpStatus.OK).body(calendarService.checkTodoInCalendar(thatDay, workType, myPlantNo, userDetails));

    }

    @PostMapping("/calendar/spraying/{yearmonthday}/{myPlantNo}")
    public ResponseEntity<SprayingDayResponstDto> checkSpraying(@PathVariable String yearmonthday, @PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        LocalDate thatDay = datePhashing.getDate(yearmonthday);

        return ResponseEntity.status(HttpStatus.OK).body(calendarService.checkSprayingInCalendar(myPlantNo, thatDay, userDetails));
    }

    @DeleteMapping("/calendar/spraying/{yearmonthday}/{myPlantNo}")
    public ResponseEntity<?> deleteBloomingDay(@PathVariable Long myPlantNo, @PathVariable String yearmonthday, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        LocalDate thatDay = datePhashing.getDate(yearmonthday);

        return ResponseEntity.status(HttpStatus.OK).body(calendarService.delSprayingDay(myPlantNo, thatDay, userDetails));

    }

    //캘린더에서 투두취소하기
    @PatchMapping("/calendar/cancel/{yearmonthday}/{myPlantNo}/{workType}")
    public ResponseEntity<String> cancelTodoInCalendar(@PathVariable String yearmonthday,
                                                       @PathVariable Long myPlantNo,
                                                       @PathVariable String workType,
                                                       @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userDetails.getUser();

        LocalDate thatDay = datePhashing.getDate(yearmonthday);
        try {
            Todo todo = todoRepository.findByUserAndTodoTimeAndWorkTypeAndMyPlant_MyPlantNo(user, thatDay, workType, myPlantNo);
            todo.setStatus(false);
            todoRepository.save(todo);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.status(HttpStatus.OK).body("투두취소완료");
    }
}

