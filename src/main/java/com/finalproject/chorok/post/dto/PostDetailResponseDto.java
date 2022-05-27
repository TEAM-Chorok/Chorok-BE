package com.finalproject.chorok.post.dto;


import com.finalproject.chorok.common.utils.CaluateTime;
import com.finalproject.chorok.plant.model.PlantPlace;
import com.finalproject.chorok.post.dto.comment.CommentResponseDto;
import com.finalproject.chorok.post.model.Post;
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
    // 게시판 타입
    private String postType;
    // 식물위치
    private String PlantPlace;
    private String postRecentTime;
    // 게시글 좋아요
    private Boolean postLike;
    private int postLikeCount;
    // 북마크
    private Boolean postBookMark;

    // 댓글 - Comment
    private int commentCount;
    List<CommentResponseDto> commentList;

    private String profileMsg;


    // 플렌테리어
    public PostDetailResponseDto(Post post, PlantPlace plantPlace,Boolean likePostChk, Boolean bookMarkPostChk, List<CommentResponseDto> commentResponseDtos, String profileMsg) {
        this.postId=post.getPostId();
        this.nickname=post.getUser().getNickname();
        this.profileImgUrl=post.getUser().getProfileImageUrl();
        this.postTitle=post.getPostTitle();
        this.postContent=post.getPostContent();
        this.postImgUrl=post.getPostImgUrl();
        this.postType = post.getPostType().getPostType();
        this.PlantPlace = plantPlace.getPlantPlace();
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(post.getCreatedAt()));
        // 내가 게시글에 좋아요를 눌렀는지 확인
        this.postLike=likePostChk;
        // 내가 게시글에 북마크를 눌렀는지 확인
        this.postBookMark=bookMarkPostChk;
        this.postLikeCount=post.getPostLike().size();
        this.commentCount = commentResponseDtos.size();
        this.commentList=commentResponseDtos;
this.profileMsg=profileMsg;
    }

    // 게시판
    public PostDetailResponseDto(Post post, Boolean likePostChk, Boolean bookMarkPostChk, List<CommentResponseDto> commentResponseDtos) {
        this.postId=post.getPostId();
        this.nickname=post.getUser().getNickname();
        this.profileImgUrl=post.getUser().getProfileImageUrl();
        this.postTitle=post.getPostTitle();
        this.postContent=post.getPostContent();
        this.postImgUrl=post.getPostImgUrl();
        this.postType = post.getPostType().getPostType();
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(post.getCreatedAt()));
        // 내가 게시글에 좋아요를 눌렀는지 확인
        this.postLike=likePostChk;
        // 내가 게시글에 북마크를 눌렀는지 확인
        this.postBookMark=bookMarkPostChk;
        this.postLikeCount=post.getPostLike().size();
        // 댓글
        this.commentCount = commentResponseDtos.size();
        this.commentList=commentResponseDtos;
    }
}
