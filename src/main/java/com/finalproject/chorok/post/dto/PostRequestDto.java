package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {

    private String postTitle;
    private String postContent;
    private String postImgUrl;
    private String plantPlaceCode;

}
