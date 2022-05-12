package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.PlantLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantLevelRepository extends JpaRepository<PlantLevel,String> {
    PlantLevel findByPlantLevelCode(String plantLevelCode);
}
