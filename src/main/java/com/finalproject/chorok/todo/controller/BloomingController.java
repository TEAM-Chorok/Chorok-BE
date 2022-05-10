package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.BloomingDayRequestDto;
import com.finalproject.chorok.todo.model.BloomingDay;
import com.finalproject.chorok.todo.service.BloomingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BloomingController {
    private final BloomingService bloomingService;
    @PostMapping("/blooming/{myPlantNo}")
    public BloomingDay createBloomingDay (@PathVariable Long myPlantNo, @RequestBody BloomingDayRequestDto bloomingDayRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

    return bloomingService.createBloomingDay(myPlantNo,bloomingDayRequestDto,userDetails);
    }
}
