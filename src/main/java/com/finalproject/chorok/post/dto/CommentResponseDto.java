package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentNo;
    private String nickname;
    private String profileImgUrl;
    private String commentContent;
    //private String commentRecentTime;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


}
