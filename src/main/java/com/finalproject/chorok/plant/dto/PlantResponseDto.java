package com.finalproject.chorok.plant.dto;

import com.finalproject.chorok.mypage.model.PlantBookMark;
import com.finalproject.chorok.plant.model.PlantPlace;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class PlantResponseDto {
    private Long plantNo;
    private String plantName;
    private String plantImgUrl;
    private String plantLevel;
    private String plantPlace;
    private String plantType;
    private String plantGrowthShape;
    private String plantTemp;
    private String plantHumid;
    private String plantInfo;
    private String springWaterCycle;
    private String summerWaterCycle;
    private String fallWaterCycle;
    private String winterWaterCycle;

    // 2022.05.21 추가 - 북마크
    private Boolean plantBookMark;

    public PlantResponseDto(Long plantNo,String plantName,String plantImgUrl,String plantLevel,String plantPlace,String plantType,String plantGrowthShape, String plantTemp, String plantHumid,String plantInfo,String springWaterCycle,String summerWaterCycle,String fallWaterCycle,String winterWaterCycle,PlantBookMark plantBookMark){
        this.plantNo = plantNo;
        this.plantName = plantName;
        this.plantImgUrl = plantImgUrl;
        this.plantLevel = plantLevel;
        this.plantPlace = plantPlace;
        this.plantType = plantType;
        this.plantGrowthShape = plantGrowthShape;
        this.plantHumid = plantHumid;
        this.plantTemp = plantTemp;
        this.plantInfo = plantInfo;
        this.springWaterCycle = springWaterCycle;
        this.summerWaterCycle = summerWaterCycle;
        this.fallWaterCycle = fallWaterCycle;
        this.winterWaterCycle = winterWaterCycle;
        this.plantBookMark = plantBookMark != null;
    }


}
