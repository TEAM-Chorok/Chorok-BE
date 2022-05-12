package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.PlantType;
import com.finalproject.chorok.plant.service.PlantFilterService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantTypeRepository extends JpaRepository<PlantType,String> {
    PlantType findByPlantTypeCode(String plantTypeCode);


}
