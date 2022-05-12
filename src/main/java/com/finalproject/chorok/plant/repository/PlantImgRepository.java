package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.PlantImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantImgRepository extends JpaRepository<PlantImg,Long> {
    @Query(nativeQuery = true,value = "SELECT * FROM plant_img WHERE plant_name like  %:keyword%")
    List<PlantImg> plantSearchToPlantNameQuery(String keyword);
    PlantImg findByPlantNo(Long plantNo);

}
