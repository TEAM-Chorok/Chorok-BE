package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.todo.service.RandomMassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@EnableScheduling
public class RandomMassageController {
    private final RandomMassageService randomMassageService;
    @Scheduled(cron = "0 0/5 * * * *")
    @GetMapping("/random")
    public ResponseEntity<String> getRandomMassage(){
        System.out.println(randomMassageService.getRandomMassege());
        return ResponseEntity.status(HttpStatus.OK).body(randomMassageService.getRandomMassege());

    }
}
