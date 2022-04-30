package com.finalproject.chorok.todo.repository;


import com.finalproject.chorok.todo.model.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {
//    List<MyPlant> findAllByUserAndAndMyPlantInfoNo(Long UserId, Long MyPlantInfoNo);

}
