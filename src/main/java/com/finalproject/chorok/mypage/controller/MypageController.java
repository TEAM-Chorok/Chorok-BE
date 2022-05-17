package com.finalproject.chorok.mypage.controller;

import com.finalproject.chorok.mypage.service.MypageService;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    // 추후에 옮길예정
    @PostMapping("/plantBookMark/{plantNo}")
    public ResponseEntity<HashMap<String, String>> plantBookMark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantNo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(mypageService.plantBookMark(plantNo, userDetails.getUser()));

    }
    // 내가쓴 플렌테리어
    @GetMapping("/mypage/photo")
    public ResponseEntity<?> myPhoto(@AuthenticationPrincipal UserDetailsImpl userDetails){
        mypageService.myPhoto(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
    // 내가 북마크한 게시물
    @GetMapping("/mypage/postBookMark")
    public ResponseEntity<?> myPostBookMark(@AuthenticationPrincipal UserDetailsImpl userDetails){
        mypageService.myPostBookMark(userDetails);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }

    // 내가 쓴 게시물


    // 내가 북마크한 식물들
}

