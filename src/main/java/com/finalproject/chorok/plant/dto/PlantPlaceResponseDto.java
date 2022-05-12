package com.finalproject.chorok.plant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
public class PlantPlaceResponseDto {
    String plantPlaceName;

public PlantPlaceResponseDto(String plantPlaceName){
        this.plantPlaceName = plantPlaceName;
    }
}
