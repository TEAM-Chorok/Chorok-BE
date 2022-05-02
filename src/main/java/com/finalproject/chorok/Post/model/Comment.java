package com.finalproject.chorok.Post.model;

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
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", unique = true, nullable = false)
    private Long commentNo;

    @Column(nullable = false)
    private String commentContent;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

//    @ManyToOne
//    @JoinColumn(name = "username", referencedColumnName = "username")
//    private User user;

}
