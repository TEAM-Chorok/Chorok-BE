package com.finalproject.chorok.Post.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
