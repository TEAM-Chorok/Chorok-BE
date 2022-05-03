package com.finalproject.chorok.MyPlant.repository;


import com.finalproject.chorok.MyPlant.model.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {
//    List<MyPlant> findAllByUserAndAndMyPlantInfoNo(Long userId, Long MyPlantInfoNo);
List<MyPlant> findAllByUserUserId(Long userUserId);
}
