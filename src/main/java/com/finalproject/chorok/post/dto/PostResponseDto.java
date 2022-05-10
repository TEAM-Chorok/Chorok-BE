package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * [Dto] - 게시판 조회 responseDto
 *
 * @class   : PostResponseDto
 * @author  : 김주호
 * @since   : 2022.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String postTitle;
    private String postImgUrl;

    public PostResponseDto(Long postId, String postTitle, String postImgUrlNo) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postImgUrl = postImgUrlNo;
    }
}
