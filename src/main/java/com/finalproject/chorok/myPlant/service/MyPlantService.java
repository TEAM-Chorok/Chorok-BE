package com.finalproject.chorok.myPlant.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.myPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.myPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.todo.dto.TodoOnlyResponseDto;
import com.finalproject.chorok.todo.model.Todo;
import com.finalproject.chorok.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        todoRepository.save(new Todo("영양제", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("분갈이", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));
        todoRepository.save(new Todo("잎닦기", myPlant.getStartDay(), myPlant.getStartDay(), false, user, myPlant));

    }

    //나의 식물들과 그에딸린 모든 투두들 보기
    public List<MyPlantResponseDto> getMyPlant(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
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
                    (int) (LocalDate.now().toEpochDay() - todoRepository.findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByLastWorkTimeDesc(user, todo.getMyPlant(), true, todo.getWorkType()).getLastWorkTime().toEpochDay()),
                    todo.isStatus()
            );
            todoOnlyResponseDtos.add(todoOnlyResponseDto);
        }
        return getMyPlantResponseDtos(myPlants, myPlantResponseDtos, todoOnlyResponseDtos);
    }

    private List<MyPlantResponseDto> getMyPlantResponseDtos(List<MyPlant> myPlants, List<MyPlantResponseDto> myPlantResponseDtos, List<TodoOnlyResponseDto> todoOnlyResponseDtos) {
        for (MyPlant myPlant : myPlants) {
            MyPlantResponseDto myPlantResponseDto = new MyPlantResponseDto(
                    myPlant.getMyPlantNo(),
                    myPlant.getPlantNo(),
                    myPlant.getMyPlantPlace(),
                    myPlant.getMyPlantImgUrl(),
                    myPlant.getMyPlantName(),
                    myPlant.getStartDay(),
                    myPlant.getEndDay(),
                    todoOnlyResponseDtos.stream().filter(h -> h.getMyPlantNo().equals(myPlant.getMyPlantNo())).collect(Collectors.toList()));
            myPlantResponseDtos.add(myPlantResponseDto);
        }

        return myPlantResponseDtos;
    }

    //날짜별로 투두 맞는것만
    //나의 식물들 보기
    public List<MyPlantResponseDto> getMyPlantForTodo(UserDetailsImpl userDetails) {
        //유저별 모든 식물 찾아오기
        User user = userDetails.getUser();

        List<MyPlant> myPlants = myPlantRepository.findAllByUser(user);
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
        //todoResponseDto에 넣어주기
        //작업수행여부에 상관없이 다 가져와서 보여줌.
        List<Todo> todos = todoRepository.findAllByUserAndTodoTime(userDetails.getUser(), LocalDate.now());
        List<TodoOnlyResponseDto> todoOnlyResponseDtos = new ArrayList<>();

        for (Todo todo : todos) {
            TodoOnlyResponseDto todoOnlyResponseDto = new TodoOnlyResponseDto(
                    todo.getTodoNo(),
                    todo.getMyPlant().getMyPlantNo(),
                    todo.getWorkType(),
                    todo.getLastWorkTime(),
                    //워크타입별 스테이터스가 true인 값과 오늘의 날짜차이. 즉, 몇일이 지났는지.
                    (int) (LocalDate.now().toEpochDay() - todoRepository.findFirstByUserAndMyPlantAndStatusAndWorkTypeOrderByLastWorkTimeDesc(user, todo.getMyPlant(), true, todo.getWorkType()).getLastWorkTime().toEpochDay()),
                    todo.isStatus()
            );
            todoOnlyResponseDtos.add(todoOnlyResponseDto);
        }
        return getMyPlantResponseDtos(myPlants, myPlantResponseDtos, todoOnlyResponseDtos);
    }


}
