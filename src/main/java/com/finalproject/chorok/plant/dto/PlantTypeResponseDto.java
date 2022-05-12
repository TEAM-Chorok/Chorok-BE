package com.finalproject.chorok.plant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PlantTypeResponseDto {
    String plantType;

    public PlantTypeResponseDto (String plantType){
        this.plantType = plantType;
    }

}
