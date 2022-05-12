package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.Plant;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.finalproject.chorok.post.model.Post;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface PlantRepository extends JpaRepository<Plant,String> {
    Plant findByPlantNo(Long plantNo);
    //레이블링
    @Query(value = "select * from plant p where p.plant_level_code=:answer1 and p.plant_place_code like %:answer2% and plant_type_code like %:answer3% and plant_growth_shape_code like %:answer4% order by rand() LIMIT 1", nativeQuery = true)
    Plant searchOnePlantByLabeling(@Param("answer1") String answer1, @Param("answer2") String answer2, @Param("answer3") String answer3, @Param("answer4") String answer4);

    //위치, category, 검색(searchtitle)를 받아서 (appear) 보여주는 정보만 보여주면서 정렬한다. 기준 cost
//    @Query(value = "select * from plant p where p.plant_level_code=:answer1 and p.plant_place_code like %:answer2% and plant_type_code like %:answer3% and plant_growth_shape_code like %:answer4% order by p.plant_no asc", nativeQuery = true)
    @Query(value = "select * from plant p where p.plant_level_code=:answer1 and p.plant_place_code like %:answer2% and plant_type_code like %:answer3% and plant_growth_shape_code like %:answer4% order by rand() LIMIT 1", nativeQuery = true)
    Plant searchOnePlantByLabeling(@Param("answer1") String answer1, @Param("answer2") String answer2, @Param("answer3") String answer3, @Param("answer4") String answer4);

    @Query(value = "select * from plant p where p.plant_level_code=:answer1 and p.plant_place_code like %:answer2% and plant_type_code like %:answer3% and plant_growth_shape_code like %:answer4% order by rand() LIMIT 3", nativeQuery = true)
    List<Plant> searchThreePlantByLabeling(@Param("answer1") String answer1, @Param("answer2") String answer2, @Param("answer3") String answer3, @Param("answer4") String answer4);



}

