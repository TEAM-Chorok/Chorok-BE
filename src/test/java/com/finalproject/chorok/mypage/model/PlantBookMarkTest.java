package com.finalproject.chorok.mypage.model;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static com.finalproject.chorok.post.model.QPost.post;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlantBookMarkTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

    }

    // 내가 북마크한 게시물
    @Test
    public void myPostBookMark(){

    }


    private BooleanExpression postTypeCode(String postTypeCode) {
        return postTypeCode == null ? null : post.postType.postTypeCode.eq(postTypeCode);
    }
    private BooleanExpression postPlaceCode(String plantPlaceCode) {
        return plantPlaceCode == null ? null : post.plantPlaceCode.eq(plantPlaceCode);
    }
}