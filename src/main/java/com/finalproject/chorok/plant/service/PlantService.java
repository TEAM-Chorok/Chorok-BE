package com.finalproject.chorok.plant.service;

import com.finalproject.chorok.common.utils.PlantUtils;
import com.finalproject.chorok.plant.dto.PlantPlaceResponseDto;
import com.finalproject.chorok.plant.dto.PlantResponseDto;
import com.finalproject.chorok.plant.dto.PlantTypeResponseDto;
import com.finalproject.chorok.plant.model.Plant;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlantService {
    private final PlantRepository plantRepository;
    private final PlantLevelRepository plantLevelRepository;
    private final PlantPlaceRepository plantPlaceRepository;
    private final PlantTypeRepository plantTypeRepository;
    private final PlantImgRepository plantImgRepository;
    private final PlantUtils plantUtils;

    public PlantResponseDto getPlantDetail(Long plantNo) {
        Plant plant = plantRepository.findByPlantNo(plantNo);
        //식물생육에 좋은 장소 붙여서 넣어주기
        String code = plant.getPlantPlaceCode();
        List<String> cods = Arrays.asList(code.split(","));
        List<PlantPlaceResponseDto> plantPlaceResponseDtos = new ArrayList<>();
        String placeAll = "";
        for (int i = 0; i < cods.size(); i++) {

            String place = plantPlaceRepository.findByPlantPlaceCode(cods.get(i)).getPlantPlace();
            placeAll += place + ",";
            PlantPlaceResponseDto plantPlaceResponseDto = new PlantPlaceResponseDto(place);

            plantPlaceResponseDtos.add(plantPlaceResponseDto);
        }

        String typeCode = plant.getPlantTypeCode();
        List<String> typeCodes = Arrays.asList(typeCode.split(","));
        List<PlantTypeResponseDto> plantTypeResponseDtos = new ArrayList<>();
        String typeAll = "";
        for (int i = 0; i < typeCodes.size(); i++) {

            String type = plantTypeRepository.findByPlantTypeCode(typeCodes.get(i)).getPlantType();
            typeAll += type + ",";
            PlantTypeResponseDto plantTypeResponseDto = new PlantTypeResponseDto(type);

            plantTypeResponseDtos.add(plantTypeResponseDto);
        }
        String finalPlace = placeAll.substring(0, placeAll.length() - 1);
        String finalType = typeAll.substring(0, typeAll.length() - 1);

        PlantResponseDto plantResponseDto = new PlantResponseDto(
                plantNo,
                plant.getPlantName(),
                plantUtils.getPlantThumbImg(plantNo),
                plantLevelRepository.findByPlantLevelCode(plant.getPlantLevelCode()).getPlantLevel(),
                //코드 여러개에 따른 여러 장소 나와야 함.
                finalPlace,
                finalType,
                plant.getGrowthTemp(),
                plant.getPlantHumid(),
                plant.getPlantAdvise(),
                plant.getWaterCycleSpringCode(),
                plant.getWaterCycleSummerCode(),
                plant.getWaterCycleAutumnCode(),
                plant.getWaterCycleWinterCode()
        );
        return plantResponseDto;
    }
}
