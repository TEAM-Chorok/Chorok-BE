package com.finalproject.chorok.MyPlant.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.MyPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.MyPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.MyPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.dto.TodoOnlyResponseDto;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPlantService {

    private final MyPlantRepository myPlantRepository;
    private final TodoRepository todoRepository;

    //투두리스트를 처음에 자동으로 저장해줌.
    @Transactional
    public void addMyPlant(MyPlantRequestDto myPlantRequestDto, User user) {
        MyPlant myPlant = new MyPlant(myPlantRequestDto, user);
        myPlantRepository.save(myPlant);
        todoRepository.save(new Todo("물주기", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("영양제 주기", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("분갈이", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));

    }

    //나의 식물들 보기
    public List<MyPlantResponseDto> getMyPlant(UserDetailsImpl userDetails) {
        List<MyPlant> myPlants = myPlantRepository.findAllByUser(userDetails.getUser());
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
        //TodoOnlyResponseDto에 값 넣어주기
        List<Todo> todos = todoRepository.findAllByUser(userDetails.getUser());
        List<TodoOnlyResponseDto> todoOnlyResponseDtos = new ArrayList<>();

        for (Todo todo : todos) {
            TodoOnlyResponseDto todoOnlyResponseDto = new TodoOnlyResponseDto(
                    todo.getTodoNo(),
                    todo.getMyPlant().getMyPlantNo(),
                    todo.getWorkType(),
                    todo.getLastWorkTime(),
                    todo.getTodoTime(),
                    todo.isStatus()
            );
            todoOnlyResponseDtos.add(todoOnlyResponseDto);
        }
        for (MyPlant myPlant : myPlants) {
            MyPlantResponseDto myPlantResponseDto = new MyPlantResponseDto(
                    myPlant.getMyPlantNo(),
                    myPlant.getPlantNo(),
                    myPlant.getMyPlantPlace(),
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantName(),
                    myPlant.getStartDay(),
                    myPlant.getEndDay(),
                    todoOnlyResponseDtos.stream());
//                    todoRepository.findAllByMyPlant_MyPlantNoOrderByWorkTypeAsc(myPlant.getMyPlantNo()));
                    myPlantResponseDtos.add(myPlantResponseDto);
        }

        return myPlantResponseDtos;
    }

    //날짜별로 투두 맞는것만
    //나의 식물들 보기
    public List<MyPlantResponseDto> getMyPlantForTodo(UserDetailsImpl userDetails) {
        List<MyPlant> myPlants = myPlantRepository.findAllByUser(userDetails.getUser());
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
        //todoResponseDto에 넣어주기
        List<Todo> todos = todoRepository.findAllByUserAndTodoTime(userDetails.getUser(), LocalDate.now().minusDays(7));
        List<TodoOnlyResponseDto> todoOnlyResponseDtos = new ArrayList<>();

        for (Todo todo : todos) {
            TodoOnlyResponseDto todoOnlyResponseDto = new TodoOnlyResponseDto(
                    todo.getTodoNo(),
                    todo.getMyPlant().getMyPlantNo(),
                    todo.getWorkType(),
                    todo.getLastWorkTime(),
                    todo.getTodoTime(),
                    todo.isStatus()
            );
            todoOnlyResponseDtos.add(todoOnlyResponseDto);
        }
        for (MyPlant myPlant : myPlants) {
            MyPlantResponseDto myPlantResponseDto = new MyPlantResponseDto(
                    myPlant.getMyPlantNo(),
                    myPlant.getPlantNo(),
                    myPlant.getMyPlantPlace(),
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantName(),
                    myPlant.getStartDay(),
                    myPlant.getEndDay(),
                    todoOnlyResponseDtos.stream().filter(h->h.getMyPlantNo().equals(myPlant.getMyPlantNo())));
            myPlantResponseDtos.add(myPlantResponseDto);
        }

        return myPlantResponseDtos;
    }

}
