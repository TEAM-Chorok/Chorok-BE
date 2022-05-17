package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@Setter
public class PostRequestDto {

    private String postTitle;
    private String postContent;
    private MultipartFile postImgUrl;
    private String plantPlaceCode;

}
