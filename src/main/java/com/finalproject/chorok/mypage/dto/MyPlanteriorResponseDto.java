package com.finalproject.chorok.mypage.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class MyPlanteriorResponseDto {
    private Long postId;
    private String postImgUrl;
    private String plantPlace;

    public MyPlanteriorResponseDto(Long postId, String postImgUrl, String plantPlace) {
        this.postId = postId;
        this.postImgUrl = postImgUrl;
        this.plantPlace = plantPlace;
    }
}
