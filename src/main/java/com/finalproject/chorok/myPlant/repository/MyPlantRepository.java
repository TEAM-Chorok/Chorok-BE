package com.finalproject.chorok.myPlant.repository;


import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.model.MyPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyPlantRepository extends JpaRepository<MyPlant, Long> {
    //    List<MyPlant> findAllByUserAndAndMyPlantInfoNo(Long userId, Long MyPlantInfoNo);
    List<MyPlant> findAllByUserUserId(Long userUserId);

    MyPlant findByUserAndMyPlantNo(User user, Long myPlantNo);

    List<MyPlant> findAllByUser(User user);

    MyPlant findByMyPlantNo(Long myPlantNo);

    MyPlant findAllByUserAndMyPlantNo(User user, Long myPlantNo);
    List<MyPlant> findByUserAndMyPlantPlace(User user, String myPlantPlace);

    @Query(nativeQuery = true, value = "SELECT * FROM my_plant WHERE my_plant.user_id = :user AND my_plant.my_plant_place=:myPlantPlace")
    List<MyPlant> myPlantForPlaceQuery(User user, String myPlantPlace);

    MyPlant findByMyPlantNoAndUser(Long myPlantNo, User user);
}
