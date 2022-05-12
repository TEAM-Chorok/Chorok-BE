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
    private String postContent;
    private Long postLikeCount;
    private Long commentCount;
    private Boolean postLike;
    private Boolean postBookMark;
    private String postRecentTime;


//    public CommunityResponseDto(Post communityPost,Boolean likePostChk, Boolean bookMarkPostChk){
//        this.postId = communityPost.getPostId();
//        this.nickname = communityPost.getUser().getNickname();
//        this.profileImgUrl = communityPost.getUser().getProfileImageUrl();
//        this.postContent = communityPost.getPostContent();
//        this.postType = communityPost.getPostType().getPostType();
//        this.postImgUrl=communityPost.getPostImgUrl();
////        this.postLikeCount= communityPost.getPostLike().size();
////        this.commentCount=communityPost.getCommentList().size();
//        this.postLike = likePostChk;
//        this.postBookMark = bookMarkPostChk;
//        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(communityPost.getCreatedAt()));
//
//    }
//
//    public CommunityResponseDto(Post communityPost) {
//        this.postId = communityPost.getPostId();
//        this.nickname = communityPost.getUser().getNickname();
//        this.profileImgUrl = communityPost.getUser().getProfileImageUrl();
//        this.postContent = communityPost.getPostContent();
//        this.postType = communityPost.getPostType().getPostType();
//        this.postImgUrl=communityPost.getPostImgUrl();
////        this.postLikeCount= communityPost.getPostLike().size();
////        this.commentCount=communityPost.getCommentList().size();
//        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(communityPost.getCreatedAt()));
//    }

    public CommunityResponseDto(Long postId, String nickname, String profileImgUrl, String postType, String postImgUrl, String postContent,
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


    public CommunityResponseDto(Long postId, String nickname, String profileImgUrl, String postType, String postImgUrl, String postContent, LocalDateTime postRecentTime) {
        this.postId = postId;
        this.nickname = nickname;
        this.profileImgUrl = profileImgUrl;
        this.postType = postType;
        this.postImgUrl = postImgUrl;
        this.postContent = postContent;
        this.postRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(postRecentTime));
    }
}
