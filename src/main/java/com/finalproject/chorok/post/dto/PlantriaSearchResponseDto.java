package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlantriaSearchResponseDto {
    private Long postId;
    private String postImgUrl;

    public PlantriaSearchResponseDto(Long postId, String postImgUrl) {
        this.postId= postId;
        this.postImgUrl=postImgUrl;
    }
}
