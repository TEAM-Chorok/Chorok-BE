package com.finalproject.chorok.MyPlant.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.MyPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.MyPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.MyPlant.repository.MyPlantRepository;
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

    @Transactional
    public MyPlant addMyPlant(MyPlantRequestDto myPlantRequestDto, User user) {

        MyPlant myPlant = new MyPlant(myPlantRequestDto,user);
       return myPlantRepository.save(myPlant);
    }
    //나의 식물들 보기
    public List<MyPlantResponseDto> getMyPlant(UserDetailsImpl userDetails){
        List<MyPlant> myPlants = myPlantRepository.findAllByUserUserId(userDetails.getUserId());
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
       MyPlantResponseDto myPlantResponseDto = null;
        for (MyPlant myPlant : myPlants){
            Long MyPlantInfoNo = myPlant.getMyPlantInfoNo();
            int plantNo = myPlant.getPlantNo();
             String myPlantPlace = myPlant.getMyPlantPlace();
             String myPlantImgUrl = myPlant.getMyPlantImgUrl();
             String myPlantName = myPlant.getMyPlantName();
            String startDay = myPlant.getStartDay();
            String endDay = myPlant.getEndDay();
           myPlantResponseDto = new MyPlantResponseDto(MyPlantInfoNo, plantNo, myPlantPlace, myPlantImgUrl, myPlantName, startDay,endDay);
           myPlantResponseDtos.add(myPlantResponseDto);
        }

        return myPlantResponseDtos;
    }
}
