package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.Plant;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlantRepository extends JpaRepository<Plant,String> {
    Plant findByPlantNo(Long plantNo);
    //레이블링
    @Query(value = "select * from plant p where p.plant_level_code=:answer1 and p.plant_place_code like %:answer2% and plant_type_code like %:answer3% and plant_growth_shape_code like %:answer4% order by rand() LIMIT 1", nativeQuery = true)
    Plant searchOnePlantByLabeling(@Param("answer1") String answer1, @Param("answer2") String answer2, @Param("answer3") String answer3, @Param("answer4") String answer4);

}

