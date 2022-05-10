package com.finalproject.chorok.plant.controller;

import com.finalproject.chorok.plant.model.PlantGrowthShape;
import com.finalproject.chorok.plant.model.PlantLevel;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.model.PlantType;
import com.finalproject.chorok.plant.service.PlantFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class PlantFilterController {
    private final PlantFilterService plantFilterService;
    // 식물장소 필터 리스트 조회
    @GetMapping("/plant-place")
    public List<PlantPlace> getPlantPlace(){
      return plantFilterService.getPlantPlace();
    }
    // 식물관리 레벨 필터 리스트 조회
    @GetMapping("/plant-level")
    public List<PlantLevel> getPlantLevel(){
        return plantFilterService.getPlantLevel();
    }
    // 식물타입 필터 리스트 조회
    @GetMapping("/plant-type")
    public List<PlantType> getPlantType(){
        return plantFilterService.getPlantType();
    }

    // 식물생육 형태
    @GetMapping("/plant-growth-shape")
    public List<PlantGrowthShape> getPlantGrowthShape(){
        return plantFilterService.getPlantGrowthShape();
    }
}
