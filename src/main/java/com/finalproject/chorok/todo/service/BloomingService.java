package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.BloomingDayRequestDto;
import com.finalproject.chorok.todo.model.BloomingDay;
import com.finalproject.chorok.todo.repository.BloomingDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BloomingService {
    private final MyPlantRepository myPlantRepository;
    private final BloomingDayRepository bloomingDayRepository;
    //꽃핀날 추하기
    public BloomingDay createBloomingDay(Long myPlantNo, BloomingDayRequestDto bloomingDayRequestDto, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        MyPlant myPlant = myPlantRepository.findById(myPlantNo).orElseThrow(
                () -> new IllegalArgumentException("나의식물이 존재하지 않습니다.")
        );
        LocalDate bloomingDay = bloomingDayRequestDto.getBloomingDay();

        BloomingDay bloomingDay1 = new BloomingDay(bloomingDay,myPlant,user);


        return bloomingDayRepository.save(bloomingDay1);
    }
}
