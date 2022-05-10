package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class PostWriteRequestDto {
    @NotNull(message = "게시글 제목을 입력해주세요.")
    @NotBlank(message = "게시글 제목은 비어있으면 안됩니다.")
    private String postTitle;
    @NotNull(message = "게시글 내용을 입력해주세요.")
    @NotBlank(message = "게시글 내용은 비어있으면 안됩니다.")
    private String postContent;
    private String plantPlaceCode;
    @NotNull(message = "게시글 타입을 입력해주세요.")
    @NotBlank(message = "게시글 타입은 비어있으면 안됩니다.")
    private String postTypeCode;
    private String postImgUrl;
}
