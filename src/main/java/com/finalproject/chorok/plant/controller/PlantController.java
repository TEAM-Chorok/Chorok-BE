package com.finalproject.chorok.plant.controller;

import com.finalproject.chorok.plant.dto.PlantResponseDto;
import com.finalproject.chorok.plant.service.PlantService;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;

    @GetMapping("/plant/{plantNo}")
    public PlantResponseDto getAllPlants(@PathVariable Long plantNo, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return plantService.getPlantDetail(plantNo,userDetails.getUser());
    }
}
