package com.finalproject.chorok.login.repository;



import com.finalproject.chorok.login.model.Labeling;
import com.finalproject.chorok.login.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LabelingRepository extends JpaRepository<Labeling, Long> {
    Optional<Labeling> findByUser(User user);

}