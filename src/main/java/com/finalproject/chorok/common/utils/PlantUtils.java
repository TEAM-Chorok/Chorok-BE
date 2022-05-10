package com.finalproject.chorok.common.utils;

import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.plant.repository.PlantImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlantUtils {

    private final PlantImgRepository plantImgRepository;

    public String getPlantThumbImg(Long plantNo){
        PlantImg plantImg = plantImgRepository.findById(plantNo).orElseThrow(
                ()->new NullPointerException("해당 식물이 존재하지 않습니다.")
        );
        String plantImgPrefix = plantImg.getPlantImgPrefix();
        String plantImgSuffix = "";
        if(plantImg.getPlantImgName().indexOf("|")==-1){
            plantImgSuffix = plantImg.getPlantImgName();
        }else{
            plantImgSuffix = plantImg.getPlantImgName().substring(0,plantImg.getPlantImgName().indexOf("|"));
        }

        return plantImgPrefix+plantImgSuffix;
    }
}
