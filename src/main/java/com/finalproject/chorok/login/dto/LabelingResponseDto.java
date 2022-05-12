package com.finalproject.chorok.login.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LabelingResponseDto {
    private Long plantId;
    private String plantImgUrl;
    private String plantName;



    public LabelingResponseDto(Long plantId, String plantImgUrl, String plantName) {
        this.plantId = plantId;
        this.plantImgUrl = plantImgUrl;
        this.plantName = plantName;
    }



}
