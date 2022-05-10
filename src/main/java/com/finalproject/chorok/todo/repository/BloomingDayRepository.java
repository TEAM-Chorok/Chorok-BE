package com.finalproject.chorok.todo.repository;

import com.finalproject.chorok.Login.model.User;
import com.finalproject.chorok.MyPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.BloomingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BloomingDayRepository extends JpaRepository<BloomingDay, Long> {
List<BloomingDay> findAllByUserAndMyPlantAndBloomingDayBetween(User user, MyPlant myPlant, LocalDate start,LocalDate end);
}
