package com.finalproject.chorok.mypage.controller;

import com.finalproject.chorok.mypage.service.MypageService;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

   @PostMapping("/plantBookMark/{plantNo}")
   public ResponseEntity<HashMap<String, String>> plantBookMark(
           @AuthenticationPrincipal UserDetailsImpl userDetails,
           @PathVariable Long plantNo
   ){
        return ResponseEntity.status(HttpStatus.OK).body(mypageService.plantBookMark(plantNo, userDetails.getUser()));

   }
}
