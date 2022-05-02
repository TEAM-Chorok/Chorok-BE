package com.finalproject.chorok.MyPlant.controller;

import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.MyPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.MyPlant.service.MyPlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MyPlantController {
    private final MyPlantService myPlantService;

    @PostMapping("/myplant")
    public void createMyPlant(@RequestBody MyPlantRequestDto myPlantRequestDto, UserDetailsImpl userDetails) {

        myPlantService.addMyPlant(myPlantRequestDto, userDetails);
    }
}
