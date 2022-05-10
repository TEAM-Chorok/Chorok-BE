package com.finalproject.chorok.post.dto;

import com.finalproject.chorok.common.utils.CaluateTime;
import com.finalproject.chorok.post.model.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

// 초록톡
@Getter
@NoArgsConstructor
public class CommunityResponseDto {
    private Long postId;
    private String nickname;
    private String profileImgUrl;
    private String postType;
    private String postImgUrl;
    private int postLikeCount;
    private int commentCount;
    private Boolean postLike;
    private Boolean postBookMark;
    private String postRecentTime;

    public CommunityResponseDto(Post communityPost,Boolean likePostChk, Boolean bookMarkPostChk){
        this.postId = communityPost.getPostId();
        this.nickname = communityPost.getUser().getNickname();
        this.profileImgUrl = communityPost.getUser().getProfileImageUrl();
        this.postType = communityPost.getPostType().getPostType();
        this.postImgUrl=communityPost.getPostImgUrl();
        this.postLikeCount= communityPost.getPostLike().size();
        this.commentCount=communityPost.getCommentList().size();
        this.postLike = likePostChk;
        this.postBookMark = bookMarkPostChk;
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(communityPost.getCreatedAt()));

    }

    public CommunityResponseDto(Post communityPost) {
        this.postId = communityPost.getPostId();
        this.nickname = communityPost.getUser().getNickname();
        this.profileImgUrl = communityPost.getUser().getProfileImageUrl();
        this.postType = communityPost.getPostType().getPostType();
        this.postImgUrl=communityPost.getPostImgUrl();
        this.postLikeCount= communityPost.getPostLike().size();
        this.commentCount=communityPost.getCommentList().size();
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(communityPost.getCreatedAt()));
    }
}
