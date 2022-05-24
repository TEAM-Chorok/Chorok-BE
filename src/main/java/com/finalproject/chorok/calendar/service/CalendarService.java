package com.finalproject.chorok.calendar.service;

import com.finalproject.chorok.calendar.Dto.CalendarResponseDto;
import com.finalproject.chorok.calendar.Dto.CalendarTodoResponseDto;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.BloomingDayResponstDto;
import com.finalproject.chorok.todo.dto.SprayingDayRequestDto;
import com.finalproject.chorok.todo.dto.SprayingDayResponstDto;
import com.finalproject.chorok.todo.model.BloomingDay;
import com.finalproject.chorok.todo.model.Spraying;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.BloomingDayRepository;
import com.finalproject.chorok.todo.repository.SprayingDayRepository;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final MyPlantRepository myPlantRepository;
    private final TodoRepository todoRepository;
    private final BloomingDayRepository bloomingDayRepository;
    private final SprayingDayRepository sprayingDayRepository;
    private final CommUtils commUtils;


    public CalendarResponseDto getMonthly(Long myPlantNo, LocalDate start, LocalDate end, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        MyPlant myPlant = myPlantRepository.findByMyPlantNo(myPlantNo);

        List<Todo> todos = todoRepository.findAllByUserAndMyPlantAndStatusAndTodoTimeBetween(user, myPlant, true, start, end);

        List<BloomingDay> bloomingDays = bloomingDayRepository.findAllByUserAndMyPlantAndBloomingDayBetween(user, myPlant, start, end);
        List<Spraying> sprayingDays = sprayingDayRepository.findAllByUserAndMyPlantAndAndSprayingDayBetween(user, myPlant, start, end);

        List<BloomingDayResponstDto> bloomingDayResponstDtos = new ArrayList<>();
        List<SprayingDayResponstDto> sprayingDayResponstDtos = new ArrayList<>();

        for (BloomingDay bloomingDay : bloomingDays) {
            BloomingDayResponstDto bloomingDayResponstDto = new BloomingDayResponstDto(
                    bloomingDay.getBloomingDay()
            );
            bloomingDayResponstDtos.add(bloomingDayResponstDto);
        }
        for (Spraying spraying : sprayingDays) {
            SprayingDayResponstDto sprayingDayResponstDto = new SprayingDayResponstDto(
                    spraying.getSprayingDay()
            );
            sprayingDayResponstDtos.add(sprayingDayResponstDto);
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
                calendarTodoResponseDtos.stream().filter(h -> h.getWorkType().equals("환기")).collect(Collectors.toList()),
                bloomingDayResponstDtos,
                sprayingDayResponstDtos
        );
        return calendarResponseDto;
    }

    //달력에서 투두 체크하기
    public String checkTodoInCalendar(LocalDate todoTime, String workType, Long myPlantNo, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        try {
            Todo todo = todoRepository.findByUserAndTodoTimeAndWorkTypeAndMyPlant_MyPlantNo(user, todoTime, workType, myPlantNo);
            todo.setStatus(true);
            todoRepository.saveAndFlush(todo);
            return "기존투두수정";
        } catch (NoSuchElementException e){
            Todo todo2 = new Todo(
                    workType,
                    todoRepository.findFirstByUserAndMyPlant_MyPlantNoAndWorkTypeOrderByLastWorkTimeDesc(user, myPlantNo, workType).get().getTodoTime(),
                    todoTime,
                    true,
                    userDetails.getUser(),
                    myPlantRepository.findByMyPlantNo(myPlantNo));
            todoRepository.save(todo2);
            return "노서치투두새로만들어서저장";
        }catch (NullPointerException e){
            Todo todo2 = new Todo(
                    workType,
                    myPlantRepository.findByMyPlantNo(myPlantNo).getStartDay(),
                    todoTime,
                    true,
                    userDetails.getUser(),
                    myPlantRepository.findByMyPlantNo(myPlantNo));
            todoRepository.save(todo2);
            return "널포인트투두새로만들어서저장";
        }
        //위의 todo가 없을시,,
//        if (todo == null) {
//            Todo todo2 = new Todo(
//                    workType,
//                    todoRepository.findFirstByUserAndMyPlant_MyPlantNoAndWorkTypeOrderByLastWorkTimeDesc(user, myPlantNo, workType).get().getTodoTime(),
//                    todoTime,
//                    true,
//                    userDetails.getUser(),
//                    myPlantRepository.findByMyPlantNo(myPlantNo)
//            );
//            todoRepository.save(todo2);
//            return todo2.isStatus();
//        }
//        todo.setStatus(true);
//        todoRepository.save(todo);
//        return todo.isStatus();
    }


    //분무한 날 추가하기
    public SprayingDayResponstDto checkSprayingInCalendar(Long myPlantNo, LocalDate thatDay, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        MyPlant myPlant = myPlantRepository.findById(myPlantNo).orElseThrow(
                () -> new IllegalArgumentException("나의식물이 존재하지 않습니다.")
        );
        Spraying spraying = new Spraying(thatDay, myPlant, user);
        sprayingDayRepository.save(spraying);
        SprayingDayResponstDto sprayingDayResponstDto = new SprayingDayResponstDto(spraying.getSprayingDay());
        return sprayingDayResponstDto;

    }

    @Transactional
    public HashMap<String, String> delSprayingDay(Long myPlantNo, LocalDate thatDay, UserDetailsImpl userDetails) {
        sprayingDayRepository.deleteSprayingBySprayingDayAndMyPlant_MyPlantNoAndUser(thatDay, myPlantNo, userDetails.getUser());
        return commUtils.responseHashMap(HttpStatus.OK);
    }

}
