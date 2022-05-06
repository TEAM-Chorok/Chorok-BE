package com.finalproject.chorok.Post.model;

import com.finalproject.chorok.Login.model.User;

import com.finalproject.chorok.Post.dto.CommentUpdateRequestDto;
import com.finalproject.chorok.Common.model.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * [model] - 댓글 model
 *
 * @class   : Comment
 * @author  : 김주호
 * @since   : 2022.04.30
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */

@Entity
@Getter
@NoArgsConstructor
@Table(name = "comment")
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", unique = true, nullable = false)
    private Long commentNo;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;


    public Comment(User user, Post post, String commentContent) {
        this.commentContent = commentContent;
        this.post=post;
        this.user=user;
    }

    public void update(CommentUpdateRequestDto commentUpdateRequestDto) {
        this.commentContent=commentUpdateRequestDto.getCommentContent();
    }
}
