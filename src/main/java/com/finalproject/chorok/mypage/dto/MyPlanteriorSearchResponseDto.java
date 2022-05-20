package com.finalproject.chorok.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class MyPlanteriorSearchResponseDto {
    private int myPlanteriorCount;
    private List<MyPlanteriorResponseDto> myPlanteriorList;
    private int myPlanteriorBookMarkCount;
    private List<MyPlanteriorResponseDto> myPlanteriorBookMarKList;

    public MyPlanteriorSearchResponseDto(Long myPlanteriorCount, List<MyPlanteriorResponseDto> myPlanteriorList, Long myPlanteriorBookMarkCount, List<MyPlanteriorResponseDto> myPlanteriorBookMarKList) {
        this.myPlanteriorCount = Math.toIntExact(myPlanteriorCount);
        this.myPlanteriorList = myPlanteriorList;
        this.myPlanteriorBookMarkCount = Math.toIntExact(myPlanteriorBookMarkCount);
        this.myPlanteriorBookMarKList = myPlanteriorBookMarKList;
    }
}
