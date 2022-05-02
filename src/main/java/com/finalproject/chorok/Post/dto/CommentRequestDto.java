package com.finalproject.chorok.Post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long postId;
    private String commentContent;
}
