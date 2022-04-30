package com.finalproject.chorok.todo.repository;

import com.sparta.realsample.Model.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {
    List<MyPlant> findAllById();

}
