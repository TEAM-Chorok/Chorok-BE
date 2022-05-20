package com.finalproject.chorok.mypage.controller;

import com.finalproject.chorok.mypage.dto.MyPlanteriorSearchResponseDto;
import com.finalproject.chorok.myPlant.dto.MyAllPlantDetailResponseDto;
import com.finalproject.chorok.mypage.dto.MypageMyplantFinalDto;
import com.finalproject.chorok.mypage.dto.MypageMyplantSixDto;
import com.finalproject.chorok.mypage.service.MypageService;
import com.finalproject.chorok.post.dto.CommunityResponseDto;
import com.finalproject.chorok.post.dto.PlantariaDictionaryResponseDto;
import com.finalproject.chorok.post.dto.PlantriaFilterRequestDto;
import com.finalproject.chorok.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;


    // 추후에 옮길예정
    // 식물도감 북마크
    @GetMapping("/plantBookMark/{plantNo}")
    public ResponseEntity<HashMap<String, String>> plantBookMark(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long plantNo
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(mypageService.plantBookMark(plantNo, userDetails.getUser()));

    }
    // 마이페이지 내가쓴 플렌테이어 6개, 북마크한 플렌테리어 조회 6개
    @GetMapping("/mypage/post/planterior")
    public ResponseEntity<MyPlanteriorSearchResponseDto> myPlanterior(@AuthenticationPrincipal UserDetailsImpl userDetails){

        return ResponseEntity.status(HttpStatus.OK).body( mypageService.myPlanterior(userDetails));
    }

    // 내가쓴 게시물 - 전체 조회
    @GetMapping("/mypage/post")
    public ResponseEntity<Page<CommunityResponseDto>> myPhoto
    (
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            PlantriaFilterRequestDto plantriaFilterRequestDto,
            @PageableDefault Pageable pageable
    ){

        return ResponseEntity.status(HttpStatus.OK).body( mypageService.myPhoto(userDetails, plantriaFilterRequestDto,pageable));
    }

    // 내가 북마크한 게시물
    @GetMapping("/mypage/bookmark/post")
    public ResponseEntity<Page<CommunityResponseDto>> myPostBookMark
    (
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            PlantriaFilterRequestDto plantriaFilterRequestDto,
            @PageableDefault Pageable pageable
    ){

        return ResponseEntity.status(HttpStatus.OK).body(mypageService.myPostBookMark(userDetails,plantriaFilterRequestDto,pageable));
    }

    // 내가 북마크한 식물들
    @GetMapping("/mypage/bookmark/plant")
    public ResponseEntity<PlantariaDictionaryResponseDto> myPlantBookMark
    (
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

        return ResponseEntity.status(HttpStatus.OK).body(mypageService.myPlantBookMark(userDetails));
    }

    //내 식물 보기
//    @GetMapping("/mypage/myplant")
//    public ResponseEntity<List<MyAllPlantDetailResponseDto>> myAllPlantDetailResponseDtos(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return ResponseEntity.status(HttpStatus.OK).body(mypageService.getAllMyPlantDetail(userDetails));
//    }

    //내 식물 6개 보기
    @GetMapping("/mypage/myplant")
    public ResponseEntity<MypageMyplantFinalDto> getSixMyPlant(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return ResponseEntity.status(HttpStatus.OK).body(mypageService.getSixMyplants(userDetails));
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

