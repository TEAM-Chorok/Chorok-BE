package com.finalproject.chorok.Post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class PostRespoonseDto {
    private Long postId;
    private String postTitle;
    private String postImgUrl;

    public PostRespoonseDto(Long postId, String postTitle, String postImgUrlNo) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postImgUrl = postImgUrlNo;
    }
}
