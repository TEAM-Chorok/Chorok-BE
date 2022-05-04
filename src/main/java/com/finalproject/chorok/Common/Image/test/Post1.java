package com.finalproject.chorok.Common.Image.test;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post1 {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String postTitle;

    @Column(nullable = false)
    private String postContents;

    @Column(nullable = true)
    private String imageUrl;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private String category;

    @Column(nullable = true)
    private String location;

    @Column(nullable = true)
    private String nickName;

    @Column(nullable = true)
    private String userId;

    public void update(Long postId, String postTitle, String postContents, int price) {
        this.id= postId;
        this.postTitle = postTitle;
        this.postContents  = postContents;
        this.price = price;
    }

    // 게시글 내용 수정
    public void update(Long postId, PostRequestDto1 requestDto) {
        this.id = postId;
        this.postTitle = requestDto.getPostTitle();
        this.postContents = requestDto.getPostContents();
        this.imageUrl = requestDto.getImageUrl();
        this.price = requestDto.getPrice();
        this.category =requestDto.getCategory();
    }
}
