package com.finalproject.chorok.Post.dto;


import com.finalproject.chorok.Post.model.PostLike;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.plant.model.PostBookMark;
import lombok.Getter;

import java.util.List;

/**
 * [Dto] - 게시판 상세조회 responseDto
 *
 * @class   : PostDetailResponseDto
 * @author  : 김주호
 * @since   : 2022.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Getter
public class PostDetailResponseDto {
    private Long postId;
    private String nickname;
    private String profileImgUrl;
    private String postTitle;
    private String postContent;
    private String postImgUrl;
    private String postRecentTime;
    // 식물위치
    private PlantPlace postPlace;
    // 게시글 좋아요
    private Boolean PostLike;
    private int postLikeCount;
    // 북마크
    private Boolean postBookMark;
    // 댓글 - Comment
    List<CommentResponseDto> commentList;



}
