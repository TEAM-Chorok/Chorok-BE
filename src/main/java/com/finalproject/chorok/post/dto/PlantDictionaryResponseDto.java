package com.finalproject.chorok.post.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Data
public class PlantDictionaryResponseDto {
    private Long plantNo;
    private String plantName;
    private String plantImgUrl;

    // QueryDsl
    public PlantDictionaryResponseDto(Long plantNo, String plantName, String plantImgUrl) {
        this.plantNo = plantNo;
        this.plantName = plantName;
        this.plantImgUrl = plantImgUrl;
    }

    // QueryDsl
    public PlantDictionaryResponseDto(Long plantNo, String plantName, String plantImgPrefix, String plantImgName) {
        this.plantNo = plantNo;
        this.plantName = plantName;
        this.plantImgUrl = plantUrlImg(plantImgPrefix,plantImgName);
    }

    private String plantUrlImg(String plantImgPrefix, String plantImgName) {
        String plantImgSuffix="";

        if(plantImgName.indexOf("|")==-1){
            plantImgSuffix = plantImgName;
        }else{
            plantImgSuffix = plantImgName.substring(0,plantImgName.indexOf("|"));
        }
        return plantImgPrefix+plantImgSuffix;
    }


}
