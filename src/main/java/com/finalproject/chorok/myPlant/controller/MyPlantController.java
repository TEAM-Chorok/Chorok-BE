package com.finalproject.chorok.myPlant.controller;

import com.finalproject.chorok.common.Image.ImageRepository;
import com.finalproject.chorok.common.Image.S3Uploader;
import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.myPlant.dto.*;
import com.finalproject.chorok.myPlant.model.MyPlant;
import com.finalproject.chorok.myPlant.repository.MyPlantRepository;
import com.finalproject.chorok.plant.repository.PlantPlaceRepository;
import com.finalproject.chorok.security.UserDetailsImpl;
import com.finalproject.chorok.myPlant.service.MyPlantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MyPlantController {
    private final MyPlantService myPlantService;
    private final MyPlantRepository myPlantRepository;
    private final S3Uploader S3Uploader;
    private final PlantPlaceRepository plantPlaceRepository;


    //내식물 이미지포함 등록하기
    @PostMapping("/myplant")
    public ResponseEntity<String> createMyPlant(
            @RequestParam("plantNo") String plantNo,
            @RequestParam(value = "myPlantPlaceCode") String myPlantPlaceCode,
            @RequestParam(value = "myPlantImgUrl", required = false) MultipartFile multipartFile,
            @RequestParam("myPlantName") String myPlantName,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws IOException {

        String myPlantImgUrl = "";
        if (multipartFile != null) {
            myPlantImgUrl = S3Uploader.upload(multipartFile, "static");
        }

        MyPlantRequestDto myPlantRequestDto = new MyPlantRequestDto(plantNo, myPlantPlaceCode, myPlantImgUrl, myPlantName);
        String plantPlace = plantPlaceRepository.findByPlantPlaceCode(myPlantPlaceCode).getPlantPlace();
        return ResponseEntity.status(HttpStatus.OK)
                .body(myPlantService.addMyPlant(myPlantRequestDto, plantPlace, userDetails.getUser()));
    }


    //내식물 수정하기
    @PostMapping("/myplant/update/{myPlantNo}")
    public ResponseEntity<String> updateMyPlant(@PathVariable Long myPlantNo,
                                                @RequestParam(value = "myPlantName", required = false) String myPlantName,
                                                @RequestParam(value = "myPlantPlaceCode", required = false) String myPlantPlaceCode,
                                                @RequestParam(value = "myPlantImgUrl", required = false) MultipartFile multipartFile,
                                                @RequestParam(value = "originalUrl", required = false) String originalUrl,
                                                @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(myPlantService.updateMyPlant(myPlantNo,myPlantName,myPlantPlaceCode,multipartFile,originalUrl));
    }


    //내 식물들 보기
    @GetMapping("/myplant")
    public ResponseEntity<List<AllMyPlantResponseDto>> myPlantInfo(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(myPlantService.getAllMyPlant(userDetails));
    }


    //식물 장소별로 보기
    @GetMapping("/myplant/place")
    public ResponseEntity<MyPlantForPlaceResponseDto> myAllPlantForPlace(@AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.status(HttpStatus.OK).body(myPlantService.getMyPlantsforPlace(userDetails));
    }

    //식물 죽은날 설정하기
    @PatchMapping("/myplant/end/{myPlantNo}")
    public void postEndDay(@PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody EndDayReqeustDto endDayReqeustDto) {
        User user = userDetails.getUser();
        try {
            MyPlant myPlant = myPlantRepository.findByUserAndMyPlantNo(user, myPlantNo);
            myPlant.setEndDay(endDayReqeustDto.getEndDay());
            myPlantRepository.save(myPlant);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    /*
     * 2022-05-19 추가 기능
     * 김주호
     * 내식물번호를 받아서 식물하나 정보 반환
     */
    @GetMapping("/myplant/plant/{myPlantNo}")
    public ResponseEntity<MyOnePlantResponseDto> findMyPlant(@PathVariable Long myPlantNo) {
        return ResponseEntity.status(HttpStatus.OK).body(myPlantService.findMyPlant(myPlantNo));
    }

    //내식물 삭제하기
    @DeleteMapping("/myplant/{myPlantNo}")
    public ResponseEntity<?> delMyplant(@PathVariable Long myPlantNo, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(myPlantService.delMyPlant(myPlantNo, userDetails));
    }
}
