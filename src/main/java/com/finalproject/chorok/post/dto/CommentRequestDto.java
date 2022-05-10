package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    @NotNull(message = "게시글 번호는 필수입력값입니다.")
    private Long postId;
    @NotNull(message = "댓글 내용은 필수입력값입니다.")
    private String commentContent;
}
