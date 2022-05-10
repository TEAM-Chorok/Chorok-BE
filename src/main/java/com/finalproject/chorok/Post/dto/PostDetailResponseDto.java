package com.finalproject.chorok.Post.dto;


import com.finalproject.chorok.Post.model.Comment;
import com.finalproject.chorok.Post.model.Post;
import com.finalproject.chorok.Common.utils.CaluateTime;
import com.finalproject.chorok.plant.model.PlantPlace;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
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
@NoArgsConstructor
public class PostDetailResponseDto {
    private Long postId;
    private String nickname;
    private String profileImgUrl;
    private String postTitle;
    private String postContent;
    private String postImgUrl;
    private String postRecentTime;
    // 식물위치
    private String postPlace;
    // 게시글 좋아요
    private Boolean PostLike;
    private int postLikeCount;
    // 북마크
    private Boolean postBookMark;
    // 댓글 - Comment
    List<Comment> commentList;


    public PostDetailResponseDto(Post post, PlantPlace plantPlace) {
        this.postId=post.getPostId();
        this.nickname=post.getUser().getNickname();
        this.profileImgUrl=post.getUser().getProfileImgUrl();
        this.postTitle=post.getPostTitle();
        this.postContent=post.getPostContent();
        this.postImgUrl=post.getPostImgUrl();
        this.postPlace = plantPlace.getPlantPlace();
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(post.getCreatedAt()));
      //  this.commentList=post.getCommentList();
        // 내가 게시글에 좋아요를 눌렀는지 확인
        // 내가 게시글에 북마크를 눌렀는지 확인
        this.postLikeCount=post.getPostLike().size();
    }
}
