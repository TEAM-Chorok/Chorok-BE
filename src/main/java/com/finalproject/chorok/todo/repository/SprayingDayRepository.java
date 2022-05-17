package com.finalproject.chorok.todo.repository;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.todo.model.Spraying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface SprayingDayRepository extends JpaRepository<Spraying, Long> {
List<Spraying> findAllByUserAndMyPlantAndAndSprayingDayBetween(User user, MyPlant myPlant, LocalDate start, LocalDate end);
Spraying deleteSprayingBySprayingDayAndMyPlant_MyPlantNoAndUser(LocalDate thatDay, Long myPlantNo, User user);
}
