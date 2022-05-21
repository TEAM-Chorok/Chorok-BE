package com.finalproject.chorok.plant.service;

import com.finalproject.chorok.common.utils.PlantUtils;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.mypage.model.PlantBookMark;
import com.finalproject.chorok.mypage.repository.PlantBookMarkRepository;
import com.finalproject.chorok.plant.dto.PlantGrowthResponsDto;
import com.finalproject.chorok.plant.dto.PlantPlaceResponseDto;
import com.finalproject.chorok.plant.dto.PlantResponseDto;
import com.finalproject.chorok.plant.dto.PlantTypeResponseDto;
import com.finalproject.chorok.plant.model.Plant;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.repository.*;
import com.finalproject.chorok.post.utils.CommUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final PlantGrowthShapeRepository plantGrowthShapeRepository;
    private final PlantBookMarkRepository plantBookMarkRepository;
    private final CommUtils commUtils;

    @Transactional
    public PlantResponseDto getPlantDetail(Long plantNo, User user) {
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
        String growthCode = plant.getPlantGrowthShapeCode();
        List<String> growthCods = Arrays.asList(growthCode.split(","));
        List<PlantGrowthResponsDto> plantGrowthResponsDtos = new ArrayList<>();
        String growthAll = "";
        for (int i = 0; i < growthCods.size(); i++) {

            String growth = plantGrowthShapeRepository.findByPlantGrowthShapeCode(growthCods.get(i)).getPlantGrowthShape();
            growthAll += growth + ",";
            PlantGrowthResponsDto plantGrowthResponsDto = new PlantGrowthResponsDto(growth);

            plantGrowthResponsDtos.add(plantGrowthResponsDto);
        }

        String finalPlace = placeAll.substring(0, placeAll.length() - 1);
        String finalType = typeAll.substring(0, typeAll.length() - 1);
        String finalGrowth = growthAll.substring(0, growthAll.length() - 1);


        // 내가 북마크한 식물 조회
        // PlantBookMark plantBookMark = plantBookMarkRepository.findBy()


        PlantResponseDto plantResponseDto = new PlantResponseDto(
                plantNo,
                plant.getPlantName(),
                plantUtils.getPlantThumbImg(plantNo),
                plantLevelRepository.findByPlantLevelCode(plant.getPlantLevelCode()).getPlantLevel(),
                //코드 여러개에 따른 여러 장소 나와야 함.
                finalPlace,
                finalType,
                finalGrowth,
                plant.getGrowthTemp(),
                plant.getPlantHumid(),
                plant.getPlantAdvise(),
                plant.getWaterCycleSpringCode(),
                plant.getWaterCycleSummerCode(),
                plant.getWaterCycleAutumnCode(),
                plant.getWaterCycleWinterCode(),
                commUtils.getPlantBookMark(user,plantNo)
        );
        return plantResponseDto;
    }
}
