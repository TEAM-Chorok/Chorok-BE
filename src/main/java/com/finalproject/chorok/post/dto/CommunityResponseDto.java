package com.finalproject.chorok.post.dto;

import com.finalproject.chorok.common.utils.CaluateTime;
import com.finalproject.chorok.post.model.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Timestamp;
import java.time.LocalDateTime;

// 초록톡
@Getter
@NoArgsConstructor
@ToString
public class CommunityResponseDto {
    private Long postId;
    private String nickname;
    private String profileImgUrl;
    private String postType;
    private String postImgUrl;
    private String postTitle;
    private String postContent;
    private Long postLikeCount;
    private Long commentCount;
    private Boolean postLike;
    private Boolean postBookMark;
    private String postRecentTime;
    // 2022-05-23 식물공간 추가 김주호
    private String plantPlace;



    public CommunityResponseDto(Post communityPost) {
        this.postId = communityPost.getPostId();
        this.nickname = communityPost.getUser().getNickname();
        this.profileImgUrl = communityPost.getUser().getProfileImageUrl();
        this.postContent = communityPost.getPostContent();
        this.postType = communityPost.getPostType().getPostType();
        this.postImgUrl=communityPost.getPostImgUrl();
//        this.postLikeCount= communityPost.getPostLike().size();
//        this.commentCount=communityPost.getCommentList().size();
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(communityPost.getCreatedAt()));
    }


    // querydsl -  초록톡 전체 조회 - 로그인
    public CommunityResponseDto(Long postId, String nickname, String profileImgUrl,
                                String postTitle,
                                String postType, String postImgUrl, String postContent,
                                Long postLikeCount,
                                Long commentCount,
                                Long postLike,
                                Long postBookMark,
                                LocalDateTime postRecentTime
    ) {
        this.postId = postId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.postType = postType;
        this.postImgUrl = postImgUrl;
        this.postTitle=postTitle;
        this.postContent = postContent;
        this.postLikeCount = postLikeCount;
        this.commentCount = commentCount;
        this.postLike = booleanChk(postLike);
        this.postBookMark = booleanChk(postBookMark);
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(postRecentTime));
    }

    private Boolean booleanChk(Long chkValue) {
        return chkValue == 0?false : true;
    }



    // querydsl -  초록톡 전체 조회 - 비로그인
    public CommunityResponseDto(Long postId, String nickname,
                                String profileImgUrl,
                                String postTitle,
                                String postType, String postImgUrl, String postContent, Long postLikeCount, Long commentCount, LocalDateTime postRecentTime) {
        this.postId = postId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.postType = postType;
        this.postImgUrl = postImgUrl;
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postLikeCount = postLikeCount;
        this.commentCount = commentCount;
        this.postLike = false;
        this.postBookMark =false;
        this.postRecentTime = CaluateTime.calculateTime(Timestamp.valueOf(postRecentTime));
    }

    // querydsl 마이페이지 - 내 식물공간 및 스크랩한 식물공간에 place정보 추가
    public CommunityResponseDto(Long postId, String nickname, String profileImgUrl,
                                String postTitle,
                                String postType, String postImgUrl, String postContent,
                                Long postLikeCount,
                                Long commentCount,
                                Long postLike,
                                Long postBookMark,
                                LocalDateTime postRecentTime,
                                String plantPlace

    ) {
        this.postId = postId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.postType = postType;
        this.postImgUrl = postImgUrl;
        this.postTitle=postTitle;
        this.postContent = postContent;
        this.postLikeCount = postLikeCount;
        this.commentCount = commentCount;
        this.postLike = booleanChk(postLike);
        this.postBookMark = booleanChk(postBookMark);
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(postRecentTime));
        this.plantPlace =plantPlace;
    }
}
