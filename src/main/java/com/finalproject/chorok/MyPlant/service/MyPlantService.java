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
    public void addMyPlant(MyPlantRequestDto myPlantRequestDto, UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        int plantNo = myPlantRequestDto.getPlantNo();
        String myPlantPlaceCode = myPlantRequestDto.getMyPlantPlaceCode();
        String myPlantImgUrl = myPlantRequestDto.getMyPlantImgUrl();
        String myPlantName = myPlantRequestDto.getMyPlantName();
        LocalDate startDay = myPlantRequestDto.getStartDay();
        LocalDate endDay = myPlantRequestDto.getEndDay();

        MyPlant myPlant = new MyPlant(plantNo, myPlantPlaceCode, myPlantImgUrl, myPlantName, startDay, endDay,user);
        myPlantRepository.save(myPlant);
    }
    //나의 식물 보기
    public List<MyPlantResponseDto> getMyPlant(Long userId, Long myPlantInfoNo){
        List<MyPlant> myPlants = myPlantRepository.findAllByUserAndAndMyPlantInfoNo(userId, myPlantInfoNo);
        List<MyPlantResponseDto> myPlantResponseDtos = new ArrayList<>();
       MyPlantResponseDto myPlantResponseDto = null;
        for (MyPlant myPlant : myPlants){
            int plantNo = myPlant.getPlantNo();
             String myPlantPlace = myPlant.getMyPlantPlace();
             String myPlantImgUrl = myPlant.getMyPlantImgUrl();
             String myPlantName = myPlant.getMyPlantName();
             LocalDate startDay = myPlant.getStartDay();
             LocalDate endDay = myPlant.getEndDay();
           myPlantResponseDto = new MyPlantResponseDto(plantNo, myPlantPlace, myPlantImgUrl, myPlantName, startDay,endDay);
           myPlantResponseDtos.add(myPlantResponseDto);
        }

        return myPlantResponseDtos;
    }
}
