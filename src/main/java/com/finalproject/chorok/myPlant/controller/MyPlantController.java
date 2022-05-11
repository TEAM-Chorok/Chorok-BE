package com.finalproject.chorok.myPlant.controller;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.dto.*;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.myPlant.service.MyPlantService;
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
    public ResponseEntity<?> createMyPlant(@RequestBody MyPlantRequestDto myPlantRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(myPlantService.addMyPlant(myPlantRequestDto, userDetails.getUser()));
    }

    //내 식물들 보기
    @GetMapping("/myplant")
    public List<AllMyPlantResponseDto> myPlantInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return myPlantService.getAllMyPlant(userDetails);
    }

    //내 식물 디테일까지 전체보기
    @GetMapping("/myplant/all")
    public ResponseEntity<?> myAllPlantDetailResponseDtos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(myPlantService.getAllMyPlantDetail(userDetails));
    }

    //식물 죽은날 설정하기
    @PatchMapping("/myplant/end/{myPlantNo}")
    public void postEndDay(@PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EndDayReqeustDto endDayReqeustDto) {
        User user = userDetails.getUser();
        try {
            MyPlant myPlant = myPlantRepository.findByUserAndMyPlantNo(user, myPlantNo);
            myPlant.setEndDay(endDayReqeustDto.getEndDay());
            myPlantRepository.save(myPlant);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    //내식물 수정하기
    @PatchMapping("myplant/update/{myPlantNo}")
    public ResponseEntity updateMyPlant(@PathVariable Long myPlantNo, @RequestBody MyPlantUpdateRequestDto myPlantUpdateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        myPlantService.updateMyPlant(myPlantUpdateRequestDto, myPlantNo, userDetails);
        return ResponseEntity.status(HttpStatus.OK).body(myPlantService.updateMyPlant(myPlantUpdateRequestDto, myPlantNo, userDetails));
    }
}
