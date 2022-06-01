package com.finalproject.chorok.post.model;

import com.finalproject.chorok.login.model.User;
import com.finalproject.chorok.post.dto.PostRequestDto;
import com.finalproject.chorok.post.dto.PostWriteRequestDto;
import com.finalproject.chorok.common.model.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/**
 * [model] - 게시판 model
 *
 * @class   : Post
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
@Table(name = "post")
public class Post extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", unique = true, nullable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_type_code", referencedColumnName = "post_type_code")
    private PostType postType;


    @Column(name = "plant_place_code")
    private String plantPlaceCode;


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLike;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostBookMark> postBookMark;


    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    @Lob
    private String postContent;

    @Column
    private String postImgUrl;

    // 게시글 등록
    public Post(PostWriteRequestDto post, User user, PostType postType, String postImgUrl) {
        this.user = user;
        //this.postTitle=post.getPostTitle();
        this.postTitle=post.getPostTypeCode().equals("postType01")?null:post.getPostTitle();
        this.postContent=post.getPostContent();
        this.postType = postType;
        this.postImgUrl =postImgUrl;
        this.plantPlaceCode=post.getPlantPlaceCode();
    }

    // update
    public void update(PostWriteRequestDto post, String postImgUrl ,PostType postType) {
        this.postTitle=post.getPostTitle();
        this.postContent=post.getPostContent();
        this.postImgUrl=postImgUrl;
        this.plantPlaceCode=post.getPlantPlaceCode();
        this.postType=postType;
    }

    // update 사진 삭제
    public void updateDeleteImage(PostWriteRequestDto post,PostType postType){
        this.postTitle=post.getPostTitle();
        this.postContent=post.getPostContent();
        this.postImgUrl=null;
        this.plantPlaceCode=post.getPlantPlaceCode();
        this.postType=postType;
    }

}

