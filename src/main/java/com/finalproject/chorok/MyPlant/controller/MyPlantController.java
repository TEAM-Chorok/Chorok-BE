package com.finalproject.chorok.MyPlant.controller;

import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.MyPlant.dto.MyPlantRequestDto;
import com.finalproject.chorok.MyPlant.service.MyPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MyPlantController {
    private final MyPlantService myPlantService;

    @PostMapping("/myplant")
    public void createMyPlant(@RequestBody MyPlantRequestDto myPlantRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        myPlantService.addMyPlant(myPlantRequestDto, userDetails.getUser());
    }

    @GetMapping("/myplant")
    public void myPlantInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        myPlantService.getMyPlant(userDetails);
    }
}
