package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.post.utils.CommUtils;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.BloomingDayRequestDto;
import com.finalproject.chorok.todo.model.BloomingDay;
import com.finalproject.chorok.todo.repository.BloomingDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BloomingService {
    private final MyPlantRepository myPlantRepository;
    private final BloomingDayRepository bloomingDayRepository;
    private final CommUtils commUtils;

    //꽃핀날 추가하기
    public BloomingDay createBloomingDay(Long myPlantNo, BloomingDayRequestDto bloomingDayRequestDto, UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        MyPlant myPlant = myPlantRepository.findById(myPlantNo).orElseThrow(
                () -> new IllegalArgumentException("나의식물이 존재하지 않습니다.")
        );
        LocalDate bloomingDay = bloomingDayRequestDto.getBloomingDay();

        BloomingDay bloomingDay1 = new BloomingDay(bloomingDay,myPlant,user);


        return bloomingDayRepository.save(bloomingDay1);
    }
@Transactional
    public HashMap<String, String>  delBloomingDay(Long myPlantNo, LocalDate bloomingDay, UserDetailsImpl userDetails) {
        bloomingDayRepository.deleteBloomingDayByBloomingDayAndMyPlant_MyPlantNoAndUser(bloomingDay,myPlantNo, userDetails.getUser());
        return commUtils.responseHashMap(HttpStatus.OK);
    }
}
