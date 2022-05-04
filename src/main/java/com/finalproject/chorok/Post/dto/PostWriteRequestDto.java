package com.finalproject.chorok.Post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
public class PostWriteRequestDto {
    private String postTitle;
    private String postContent;
    private String plantPlaceCode;
    private String postTypeCode;
    private String postImgUrl;
}
