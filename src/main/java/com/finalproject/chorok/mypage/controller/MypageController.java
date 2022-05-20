package com.finalproject.chorok.mypage.controller;

import com.finalproject.chorok.mypage.dto.MyPlanteriorSearchResponseDto;
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


}

