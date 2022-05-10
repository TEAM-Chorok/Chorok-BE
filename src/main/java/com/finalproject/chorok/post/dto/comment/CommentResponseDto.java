package com.finalproject.chorok.post.dto.comment;

import com.finalproject.chorok.common.utils.CaluateTime;
import com.finalproject.chorok.post.model.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String nickname;
    private String profileImgUrl;
    private String commentContent;
    private String commentRecentTime;

    public CommentResponseDto(Comment comment){
        this.commentId=comment.getCommentId();
        this.nickname=comment.getUser().getNickname();
        this.profileImgUrl=comment.getUser().getProfileImageUrl();
        this.commentContent=comment.getCommentContent();
        this.commentRecentTime= CaluateTime.calculateTime(Timestamp.valueOf(comment.getCreatedAt()));


    }

}
