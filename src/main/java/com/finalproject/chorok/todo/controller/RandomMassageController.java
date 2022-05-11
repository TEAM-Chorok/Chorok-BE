package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.todo.service.RandomMassageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RandomMassageController {
    private final RandomMassageService randomMassageService;

    @GetMapping("/random")
    public ResponseEntity<?> getRandomMassage(){
        return ResponseEntity.status(HttpStatus.OK).body(randomMassageService.getRandomMassege());

    }
}
