package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.PlantGrowthShape;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantGrowthShapeRepository extends JpaRepository<PlantGrowthShape,String> {
    PlantGrowthShape findByPlantGrowthShapeCode(String plantGrowthShapeCode);
}
