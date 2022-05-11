package com.finalproject.chorok.todo.repository;

import com.finalproject.chorok.todo.model.RandomMassage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RandomMassageRepository extends JpaRepository<RandomMassage,Integer> {
    RandomMassage findByMassageNo(int massageNo);
}
