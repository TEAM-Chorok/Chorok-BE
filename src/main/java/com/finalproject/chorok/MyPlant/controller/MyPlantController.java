package com.finalproject.chorok.MyPlant.controller;

import com.finalproject.chorok.MyPlant.dto.MyPlantResponseDto;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.MyPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.MyPlant.service.MyPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPlantController {
    private final MyPlantService myPlantService;

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
}
