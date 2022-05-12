package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PostSearchResponseDto {

    // 플랜테이어 게시물 리스트
    private int plantriaCount;
    private List<PlantriaSearchResponseDto> plantriaSearchList;
    // 식물 도감 리스트
    private int plantDictionaryCount;
    private List<PlantDictionaryResponseDto> plantDictionaryList;

    public PostSearchResponseDto(Long planteriorCount, List<PlantriaSearchResponseDto> plantriaSearchList,Long plantDictionaryCount, List<PlantDictionaryResponseDto> plantDictionarySearchList) {
        this.plantriaCount= Math.toIntExact(planteriorCount);
        this.plantriaSearchList=plantriaSearchList;
        this.plantDictionaryCount= Math.toIntExact(plantDictionaryCount);
        this.plantDictionaryList=plantDictionarySearchList;
    }

    //QueryDSl

//    public PostSearchResponseDto(Long plantriaCount, List<PlantriaSearchResponseDto> plantriaSearchList, int plantDictionaryCount, List<PlantDictionaryResponseDto> plantDictionaryList) {
//        this.plantriaCount = Math.toIntExact(plantriaCount);
//        this.plantriaSearchList = plantriaSearchList;
//        this.plantDictionaryCount = plantDictionaryCount;
//        this.plantDictionaryList = plantDictionaryList;
//    }
}
