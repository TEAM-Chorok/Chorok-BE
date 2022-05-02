package com.finalproject.chorok.MyPlant.repository;


import com.finalproject.chorok.MyPlant.model.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {
    List<MyPlant> findAllByUserAndAndMyPlantInfoNo(Long userId, Long MyPlantInfoNo);

}
