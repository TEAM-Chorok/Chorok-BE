package com.finalproject.chorok.Post.dto;

import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long commentNo;
    private String nickname;
    private String profileImgUrl;
    private String commentContent;
    private String commentRecentTime;
}
