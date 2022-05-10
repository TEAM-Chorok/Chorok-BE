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

    public PostSearchResponseDto(int plantriaCount, List<PlantriaSearchResponseDto> plantriaSearchList, List<PlantDictionaryResponseDto> plantDictionarySearchList) {
        this.plantriaCount=plantriaCount;
        this.plantriaSearchList=plantriaSearchList;
        this.plantDictionaryCount=plantDictionarySearchList.size();
        this.plantDictionaryList=plantDictionarySearchList;
    }
}
