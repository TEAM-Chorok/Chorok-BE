package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.PlantPlace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantPlaceRepository extends JpaRepository<PlantPlace,String> {
    PlantPlace findByPlantPlaceCode(String plantPlaceCode);

}
