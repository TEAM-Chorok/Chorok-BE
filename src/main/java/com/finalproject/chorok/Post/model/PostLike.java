package com.finalproject.chorok.Post.model;

import com.finalproject.chorok.Login.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * [model] - 게시판 좋아요 model
 *
 * @class   : PostLike
 * @author  : 김주호
 * @since   : 2022.04.03
 * @version : 1.0
 *
 *   수정일     수정자             수정내용
 *  --------   --------    ---------------------------
 *
 */
@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_like")
public class PostLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postLikeNo;


    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;



    public PostLike(Post post, User user) {
        this.post=post;
        this.user=user;
    }

//    @ManyToOne
//    @JoinColumn(name = "username", referencedColumnName = "username")
//    private User user;
}
