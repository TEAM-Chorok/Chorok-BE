package com.finalproject.chorok.calendar.service;

import com.finalproject.chorok.calendar.Dto.CalendarResponseDto;
import com.finalproject.chorok.calendar.Dto.CalendarTodoResponseDto;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.BloomingDayResponstDto;
import com.finalproject.chorok.todo.model.BloomingDay;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.BloomingDayRepository;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final MyPlantRepository myPlantRepository;
    private final TodoRepository todoRepository;
    private final BloomingDayRepository bloomingDayRepository;

//    public List<CalendarTodoResponseDto> getMonthly(Long myPlantNo, LocalDate start, LocalDate end, UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        MyPlant myPlant = myPlantRepository.findByMyPlantNo(myPlantNo);
//        //그달 어느날에 무엇을 했는지
//        List<Todo> todos = todoRepository.findAllByUserAndMyPlantAndStatusAndTodoTimeBetween(user, myPlant, true, start, end);
//
//        List<CalendarTodoResponseDto> calendarTodoResponseDtos = new ArrayList<>();
//        for (Todo todo : todos) {
//            CalendarTodoResponseDto calendarTodoResponseDto = new CalendarTodoResponseDto(
//                    todo.getWorkType(),
//                    todo.getTodoTime()
//            );
//            calendarTodoResponseDtos.add(calendarTodoResponseDto);
//        }
//
//        //워크타입별로 리스트 만들기
//        return calendarTodoResponseDtos;
//    }

    public CalendarResponseDto getMonthly(Long myPlantNo, LocalDate start, LocalDate end, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        MyPlant myPlant = myPlantRepository.findByMyPlantNo(myPlantNo);

        List<Todo> todos = todoRepository.findAllByUserAndMyPlantAndStatusAndTodoTimeBetween(user, myPlant, true, start, end);

        List<BloomingDay> bloomingDays = bloomingDayRepository.findAllByUserAndMyPlantAndBloomingDayBetween(user, myPlant, start, end);

        List<BloomingDayResponstDto> bloomingDayResponstDtos = new ArrayList<>();

        for (BloomingDay bloomingDay : bloomingDays) {
            BloomingDayResponstDto bloomingDayResponstDto = new BloomingDayResponstDto(
                    bloomingDay.getBloomingDay()
            );
            bloomingDayResponstDtos.add(bloomingDayResponstDto);
        }
        List<CalendarTodoResponseDto> calendarTodoResponseDtos = new ArrayList<>();
        for (Todo todo : todos) {
            CalendarTodoResponseDto calendarTodoResponseDto = new CalendarTodoResponseDto(
                    todo.getWorkType(),
                    todo.getTodoTime()
            );
            calendarTodoResponseDtos.add(calendarTodoResponseDto);
        }
        CalendarResponseDto calendarResponseDto = new CalendarResponseDto(
                calendarTodoResponseDtos.stream().filter(h -> h.getWorkType().equals("물주기")).collect(Collectors.toList()),
                calendarTodoResponseDtos.stream().filter(h -> h.getWorkType().equals("분갈이")).collect(Collectors.toList()),
                calendarTodoResponseDtos.stream().filter(h -> h.getWorkType().equals("영양제")).collect(Collectors.toList()),
                calendarTodoResponseDtos.stream().filter(h -> h.getWorkType().equals("잎닦기")).collect(Collectors.toList()),
                bloomingDayResponstDtos
        );
        return calendarResponseDto;
    }

    public Todo checkTodoInCalendar(LocalDate todoTime, String workType, Long myPlantNo, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        Todo todo = todoRepository.findByUserAndTodoTimeAndWorkTypeAndMyPlant_MyPlantNo(user, todoTime, workType, myPlantNo);
        System.out.println("todo = " + todo);
        todo.setStatus(true);
        todoRepository.save(todo);
        return todo;
    }

}
