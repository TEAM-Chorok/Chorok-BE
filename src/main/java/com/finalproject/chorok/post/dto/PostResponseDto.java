package com.finalproject.chorok.post.dto;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.post.model.Post;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Setter
public class PostResponseDto {
    private Long postId;
    private String postTitle;
    private String postImgUrl;
    private String postContent;
    private String nickname;
    private Long userId;
    private String profileImageUrl;



    public PostResponseDto(Long postId, String postTitle, String postImgUrl, String postContent, User user) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postImgUrl = postImgUrl;
        this.postContent=postContent;
        this.nickname=user.getNickname();
    }

    public PostResponseDto(Post post) {
        this.postId = post.getPostId();
        this.postTitle = post.getPostTitle();
        this.postImgUrl = post.getPostImgUrl();
        this.postContent=post.getPostContent();
        this.nickname=post.getUser().getNickname();
        this.userId = post.getUser().getUserId();
        this.profileImageUrl=post.getUser().getProfileImageUrl();

    }

    //Test

    public PostResponseDto(Long postId, String postTitle, String postImgUrl, String postContent, String nickname, Long userId, String profileImgUrl) {
        this.postId = postId;
        this.postTitle = postTitle;
        this.postImgUrl = postImgUrl;
        this.postContent = postContent;
        this.nickname = nickname;
        this.userId = userId;
        this.profileImageUrl = profileImgUrl;
    }
}
