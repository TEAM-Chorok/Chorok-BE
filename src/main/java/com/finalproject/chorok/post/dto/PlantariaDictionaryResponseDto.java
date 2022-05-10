package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class PlantariaDictionaryResponseDto {
    private int plantDictionaryCount;
    private List<PlantDictionaryResponseDto> plantList;

    public PlantariaDictionaryResponseDto(int plantDictionaryCount, List<PlantDictionaryResponseDto> plantDictionaryList) {
        this.plantDictionaryCount =plantDictionaryCount;
        this.plantList=plantDictionaryList;
    }
}
