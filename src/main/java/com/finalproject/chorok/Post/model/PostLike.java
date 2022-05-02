package com.finalproject.chorok.Post.model;

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

    @Column(columnDefinition = "boolean default true")
    private Boolean postLike;

//    @ManyToOne
//    @JoinColumn(name = "username", referencedColumnName = "username")
//    private User user;
}
