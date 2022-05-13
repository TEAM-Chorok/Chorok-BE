package com.finalproject.chorok.todo.service;

import com.finalproject.chorok.todo.model.RandomMassage;
import com.finalproject.chorok.todo.repository.RandomMassageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class RandomMassageService {
    private final RandomMassageRepository randomMassageRepository;
//랜덤메세지 출력
    public String getRandomMassege() {
        Random random = new Random();
        RandomMassage randomMassage = randomMassageRepository.findByMassageNo(random.nextInt(31));
        return randomMassage.getMassage();

    }
}
