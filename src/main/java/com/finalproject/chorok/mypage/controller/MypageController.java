package com.finalproject.chorok.mypage.controller;

import com.finalproject.chorok.login.service.UserService;
import com.finalproject.chorok.myPlant.dto.MyAllPlantDetailResponseDto;
import com.finalproject.chorok.myPlant.service.MyPlantService;
import com.finalproject.chorok.mypage.service.MypageService;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;
    private final MyPlantService myPlantService;
    private final UserService userService;

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

    //내 식물 보기
    @GetMapping("/mypage/myplant")
    public ResponseEntity<List<MyAllPlantDetailResponseDto>> myAllPlantDetailResponseDtos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(mypageService.getAllMyPlantDetail(userDetails));
    }

    //비밀번호 수정하기
    @PatchMapping("/user/update/password")
    public ResponseEntity<HashMap<String, String>> updatePassword(@RequestParam(name = "password") String password, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.status(HttpStatus.OK).body(mypageService.updatePassword(password, userDetails));
    }

    //프로필 수정하기
    @PatchMapping("/user/update/profile")
    public ResponseEntity<HashMap<String, String>> updateProfile(
            @RequestParam(value = "nickname", required = false) String nickname,
            @RequestParam(value = "profileImageUrl", required = false) MultipartFile multipartFile,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {

        return ResponseEntity.status(HttpStatus.OK).body(mypageService.updateProfile(nickname, multipartFile, userDetails));
    }
}

