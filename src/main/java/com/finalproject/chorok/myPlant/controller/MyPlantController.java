package com.finalproject.chorok.myPlant.controller;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.myPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.myPlant.service.MyPlantService;
import com.finalproject.chorok.myPlant.dto.EndDayReqeustDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPlantController {
    private final MyPlantService myPlantService;
    private final MyPlantRepository myPlantRepository;

    //내식물 등록하기
    @PostMapping("/myplant")
    public void createMyPlant(@RequestBody MyPlantRequestDto myPlantRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        myPlantService.addMyPlant(myPlantRequestDto, userDetails.getUser());
    }

    //내 식물들 보기
    @GetMapping("/myplant")
    public List<MyPlantResponseDto> myPlantInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPlantService.getMyPlant(userDetails);
    }

    //식물 죽은날 설정하기
   @PatchMapping("/myplant/end/{myPlantNo}")
    public void postEndDay(@PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EndDayReqeustDto endDayReqeustDto){
        User user = userDetails.getUser();
        try {
            MyPlant myPlant = myPlantRepository.findByUserAndMyPlantNo(user,myPlantNo);
            myPlant.setEndDay(endDayReqeustDto.getEndDay());
            myPlantRepository.save(myPlant);
        }
        catch (Exception e){
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
