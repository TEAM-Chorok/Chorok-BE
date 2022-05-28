package com.finalproject.chorok.todo.controller;

import com.finalproject.chorok.common.utils.DatePhashing;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.todo.dto.BloomingDayRequestDto;
import com.finalproject.chorok.todo.dto.BloomingDayResponstDto;
import com.finalproject.chorok.todo.model.BloomingDay;
import com.finalproject.chorok.todo.service.BloomingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class BloomingController {
    private final BloomingService bloomingService;
    private final DatePhashing datePhashing;


    @PostMapping("/blooming/{myPlantNo}")
    public ResponseEntity<BloomingDayResponstDto> createBloomingDay (@PathVariable Long myPlantNo,
                                                                     @RequestBody BloomingDayRequestDto bloomingDayRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
    return ResponseEntity.status(HttpStatus.OK).body(bloomingService.createBloomingDay(myPlantNo,bloomingDayRequestDto,userDetails));
    }

    //꽃핀날 삭제
    @DeleteMapping("/blooming/{myPlantNo}/{yearmonthday}")
    public ResponseEntity<HashMap<String, String>> deleteBloomingDay(@PathVariable Long myPlantNo, @PathVariable String yearmonthday, @AuthenticationPrincipal UserDetailsImpl userDetails){
        LocalDate thatDay = datePhashing.getDate(yearmonthday);

        return ResponseEntity.status(HttpStatus.OK).body(bloomingService.delBloomingDay(myPlantNo,thatDay,userDetails));

    }
    //push

}
