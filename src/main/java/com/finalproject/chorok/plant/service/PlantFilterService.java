package com.finalproject.chorok.plant.service;

import com.finalproject.chorok.plant.model.*;
import com.finalproject.chorok.plant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantFilterService {
    private final PlantPlaceRepository plantPlaceRepository;
    private final PlantLevelRepository plantLevelRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final PlantGrowthShapeRepository plantGrowthShapeRepository;


    public List<PlantPlace> getPlantPlace() {
        return plantPlaceRepository.findAll();
    }

    public List<PlantLevel> getPlantLevel() {
        return plantLevelRepository.findAll();
    }

    public List<PlantType> getPlantType() {
        return plantTypeRepository.findAll();
    }

    public List<PlantGrowthShape> getPlantGrowthShape() {
        return plantGrowthShapeRepository.findAll();
    }
}
