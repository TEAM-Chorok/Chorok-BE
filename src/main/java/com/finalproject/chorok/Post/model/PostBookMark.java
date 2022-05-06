package com.finalproject.chorok.Post.model;

import com.finalproject.chorok.Login.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "post_book_mark")
public class PostBookMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postBookMarkNo;

    @ManyToOne
    @JoinColumn(name = "post_id", referencedColumnName = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name="user_id",referencedColumnName = "user_id")
    private User user;

    public PostBookMark(Post post, User user) {
        this.post=post;
        this.user=user;
    }
}
