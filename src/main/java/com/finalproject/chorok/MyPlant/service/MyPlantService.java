package com.finalproject.chorok.MyPlant.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.MyPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.MyPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.MyPlant.repository.MyPlantRepository;
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
        todoRepository.save(new Todo("물주기",myPlant.getStartDay(),myPlant.getStartDay(),false,user, myPlant));
        todoRepository.save(new Todo("영양제 주기",myPlant.getStartDay(),myPlant.getStartDay(),false,user, myPlant));
        todoRepository.save(new Todo("분갈이",myPlant.getStartDay(),myPlant.getStartDay(),false,user, myPlant));

    }

    //나의 식물들 보기
    public List<MyPlantResponseDto> getMyPlant(UserDetailsImpl userDetails) {
        List<MyPlant> myPlants = myPlantRepository.findAllByUserUserId(userDetails.getUserId());
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
        MyPlantResponseDto myPlantResponseDto = null;
        for (MyPlant myPlant : myPlants) {
            Long myPlantInfoNo = myPlant.getMyPlantNo();
            int plantNo = myPlant.getPlantNo();
            String myPlantPlace = myPlant.getMyPlantPlace();
            String myPlantImgUrl = myPlant.getMyPlantImgUrl();
            String myPlantName = myPlant.getMyPlantName();
            LocalDate startDay = myPlant.getStartDay();
            LocalDate endDay = myPlant.getEndDay();
            List<Todo> todos = todoRepository.findAllByMyPlant_MyPlantNoOrderByWorkTypeAsc(myPlantInfoNo);
            myPlantResponseDto = new MyPlantResponseDto(myPlantInfoNo, plantNo, myPlantPlace, myPlantImgUrl, myPlantName, startDay, endDay, todos);
            myPlantResponseDtos.add(myPlantResponseDto);
        }

        return myPlantResponseDtos;
    }
}
