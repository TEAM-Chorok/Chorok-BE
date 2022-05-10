package com.finalproject.chorok.plant.repository;

import com.finalproject.chorok.plant.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant,String> {

}
