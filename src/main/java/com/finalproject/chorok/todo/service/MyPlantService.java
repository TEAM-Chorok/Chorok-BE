package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.MyPlantRequestDto;
import com.finalproject.chorok.todo.model.MyPlant;
import com.finalproject.chorok.todo.repository.MyPlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
}
