package com.finalproject.chorok.mypage.repository;

import com.finalproject.chorok.mypage.model.PlantBookMark;
import com.finalproject.chorok.post.model.PostBookMark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantBookMarkRepository extends JpaRepository<PlantBookMark, Long> {
    @Query(value = "SELECT * FROM plant_book_mark a WHERE a.user_id=:userId AND a.plant_no =:plantNo",nativeQuery = true)
    PlantBookMark findUserPlantBookMarkQuery(@Param("userId") Long userId, @Param("plantNo") Long plantNo);

    @Modifying
    @Query(value = "DELETE FROM post_book_mark WHERE user_id=:userId And plant_no=:plantNo",nativeQuery = true)
    void deleteByUserPlantBookMarkQuery(@Param("userId") Long userId, @Param("plantNo") Long plantNo);

}
