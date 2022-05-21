package com.finalproject.chorok.mypage.model;

import com.finalproject.chorok.mypage.dto.MyPlanteriorResponseDto;
import com.finalproject.chorok.plant.model.QPlant;
import com.finalproject.chorok.plant.model.QPlantPlace;
import com.finalproject.chorok.post.dto.CommunityResponseDto;
import com.finalproject.chorok.post.dto.PlantDictionaryResponseDto;
import com.finalproject.chorok.post.model.QPostBookMark;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import java.util.List;

import static com.finalproject.chorok.mypage.model.QPlantBookMark.*;
import static com.finalproject.chorok.plant.model.QPlant.*;
import static com.finalproject.chorok.plant.model.QPlantImg.plantImg;
import static com.finalproject.chorok.plant.model.QPlantPlace.*;
import static com.finalproject.chorok.post.model.QComment.comment;
import static com.finalproject.chorok.post.model.QPost.post;
import static com.finalproject.chorok.post.model.QPostBookMark.postBookMark;
import static com.finalproject.chorok.post.model.QPostLike.postLike;
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
    // 마이페이지 -  플렌테리어 , 스크랩한 사진 같이 조회

    // 내가 작성한 플렌테이어사진 전체
    @Test
    public void myplanterior(){
        Long userId = 9L;
        String postTypeCode = "postType02";
        String plantPlaceCode = "";
        List<CommunityResponseDto> result = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postType.postType,
                                post.postImgUrl,
                                post.postContent,
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(postLike.count())
                                                .from(postLike)
                                                .where(postLike.post.postId.eq(post.postId)),
                                        "postLikeCount"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(comment.count())
                                                .from(comment)
                                                .where(comment.post.postId.eq(post.postId)),
                                        "commentCount"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(postLike.count())
                                                .from(postLike)
                                                .where(
                                                        postLike.user.userId.eq(userId)
                                                                .and(postLike.post.postId.eq(post.postId))
                                                ), "postLike"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(postBookMark.count())
                                                .from(postBookMark)
                                                .where(
                                                        postBookMark.user.userId.eq(userId)
                                                                .and(postBookMark.post.postId.eq(post.postId))

                                                ), "postBookMark"),
                                post.createdAt.as("postRecentTime")
                        )
                )
                .from(post)
                .where(
                        post.user.userId.eq(userId),
                        myPagePhoto(postTypeCode),
                        plantPlaceCode(plantPlaceCode)

                )
                .fetch();
        for (CommunityResponseDto communityResponseDto : result) {
            System.out.println("communityResponseDto = " + communityResponseDto);
        }

    }

    // 마이페이지 - 스크랩한 사진 전체 조회
    @Test
    public void myBookMarkPost(){
        Long userId = 9L;
        String postTypeCode = "postType02";
        String plantPlaceCode = "";
        List<CommunityResponseDto> result = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postType.postType,
                                post.postImgUrl,
                                post.postContent,
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(postLike.count())
                                                .from(postLike)
                                                .where(postLike.post.postId.eq(post.postId)),
                                        "postLikeCount"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(comment.count())
                                                .from(comment)
                                                .where(comment.post.postId.eq(post.postId)),
                                        "commentCount"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(postLike.count())
                                                .from(postLike)
                                                .where(
                                                        postLike.user.userId.eq(userId)
                                                                .and(postLike.post.postId.eq(post.postId))
                                                ), "postLike"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(postBookMark.count())
                                                .from(postBookMark)
                                                .where(
                                                        postBookMark.user.userId.eq(userId)
                                                                .and(postBookMark.post.postId.eq(post.postId))

                                                ), "postBookMark"),
                                post.createdAt.as("postRecentTime")
                        )
                )
                .from(postBookMark)
                .where(
                        postBookMark.user.userId.eq(userId),
                        myPagePhoto(postTypeCode),
                        plantPlaceCode(plantPlaceCode)

                )
                .fetch();
        for (CommunityResponseDto communityResponseDto : result) {
            System.out.println("communityResponseDto = " + communityResponseDto);
        }

    }

    // 내가 북마크한 식물들 조회
    @Test
    public void myPlantBookMark(){
        Long userId = 9L;
        List<PlantDictionaryResponseDto> result = queryFactory
                .select(
                        Projections.constructor(PlantDictionaryResponseDto.class,
                                plant.plantNo,
                                plant.plantName,
                                plantImg.plantImgPrefix,
                                plantImg.plantImgName
                        )
                )
                .from(plant, plantImg)
                .leftJoin(plantBookMark)
                .on(plant.plantNo.eq(plantBookMark.plant.plantNo))
                .where(
                        plant.plantNo.eq(plantImg.plantNo),
                        plantBookMark.user.userId.eq(userId)
                )
                .fetch();
        for (PlantDictionaryResponseDto plantDictionaryResponseDto : result) {
            System.out.println("plantDictionaryResponseDto = " + plantDictionaryResponseDto);

        }

    }
    // 내가 쓴 글 카운트
    @Test
    public void myPlanteriorCount(){
        Long userId = 24L;
        long result = queryFactory
                .selectFrom(post)
                .where(
                        post.user.userId.eq(userId),
                        post.postType.postTypeCode.eq("postType01")
                )
                .fetchCount();
        System.out.println("result = " + result);
    }

    // 내가 쓴 글 6개 조회
    @Test
    public void myPlanterior(){
        Long userId = 24L;
        List<MyPlanteriorResponseDto> result =  queryFactory
                .select(
                        Projections.constructor(MyPlanteriorResponseDto.class,
                                post.postId,
                                post.postImgUrl,
                                ExpressionUtils.as(
                                        plantPlace1.plantPlace,
                                        "plantPlace"
                                )
                        )

                )
                .from(post)
                .leftJoin(plantPlace1)
                .on(post.plantPlaceCode.eq(plantPlace1.plantPlaceCode))
                .where(
                        post.user.userId.eq(userId),
                        //post.plantPlaceCode.eq(plantPlace1.plantPlaceCode),
                        post.postType.postTypeCode.eq("postType01")
                )
                .orderBy(post.createdAt.desc())
                .limit(6)
                .fetch();
        for (MyPlanteriorResponseDto myPlanteriorResponseDto : result) {
            System.out.println("myPlanteriorResponseDto = " + myPlanteriorResponseDto);

        }
    }
    // 내가 북마크한 플렌테리어 카운트
    @Test
    public void myPlanteriorBookMarkCount() {
        Long userId = 24L;
        Long result = queryFactory
                .selectFrom(post)
                .leftJoin(postBookMark)
                .on(post.postId.eq(postBookMark.post.postId))
                .where(
                        post.postType.postTypeCode.eq("postType01"),
                        postBookMark.user.userId.eq(userId)
                )
                .orderBy(post.createdAt.desc())
                .fetchCount();
        System.out.println("result = " + result);
    }
    // 내가 북마크한 플렌테리어 6개
    @Test
    public void myPlanteriorBookMark(){
        Long userId = 24L;
        List<MyPlanteriorResponseDto> result =  queryFactory
                .select(
                        Projections.constructor(MyPlanteriorResponseDto.class,
                                post.postId,
                                post.postImgUrl,
                                ExpressionUtils.as(
                                        plantPlace1.plantPlace,
                                        "plantPlace"
                                )
                        )

                )
                .from(post,plantPlace1)
                .leftJoin(postBookMark)
                .on(post.postId.eq(postBookMark.post.postId))
                .where(
                        post.plantPlaceCode.eq(plantPlace1.plantPlaceCode),
                        postBookMark.user.userId.eq(userId),
                        post.postType.postTypeCode.eq("postType01")
                )
                .orderBy(post.createdAt.desc())
                .limit(6)
                .fetch();
        for (MyPlanteriorResponseDto myPlanteriorResponseDto : result) {
            System.out.println("myPlanteriorResponseDto = " + myPlanteriorResponseDto);

        }
    }
    private BooleanExpression myPagePhoto(String postTypeCode) {
        return postTypeCode == null ? null : post.postType.postTypeCode.eq(postTypeCode);
    }

    private BooleanExpression postTypeCode(String postTypeCode) {
        return postTypeCode == null ? null : post.postType.postTypeCode.eq(postTypeCode);
    }
    private BooleanExpression plantPlaceCode(String plantPlaceCode) {
        return (plantPlaceCode==null || plantPlaceCode.equals("")) ? null : post.plantPlaceCode.eq(plantPlaceCode);
    }
}