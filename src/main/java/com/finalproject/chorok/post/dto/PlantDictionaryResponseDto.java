package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantDictionaryResponseDto {
    private Long plantNo;
    private String plantName;
    private String plantImgUrl;

    public PlantDictionaryResponseDto(Long plantNo, String plantName, String plantThumbImg) {
        this.plantNo = plantNo;
        this.plantName = plantName;
        this.plantImgUrl = plantThumbImg;
    }
}
