package com.finalproject.chorok.post.model;

import com.finalproject.chorok.login.model.QUser;
import com.finalproject.chorok.plant.model.Plant;
import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.plant.model.QPlant;
import com.finalproject.chorok.plant.model.QPlantImg;
import com.finalproject.chorok.post.dto.CommunityResponseDto;
import com.finalproject.chorok.post.dto.PlantDictionaryResponseDto;
import com.finalproject.chorok.post.dto.PlantriaSearchResponseDto;
import com.finalproject.chorok.post.dto.PostResponseDto;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static com.finalproject.chorok.login.model.QUser.*;
import static com.finalproject.chorok.plant.model.QPlant.*;
import static com.finalproject.chorok.plant.model.QPlantImg.*;
import static com.finalproject.chorok.post.model.QComment.*;
import static com.finalproject.chorok.post.model.QPost.*;
import static com.finalproject.chorok.post.model.QPostBookMark.*;
import static com.finalproject.chorok.post.model.QPostLike.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostTest {
    @Autowired
    EntityManager em;
    JPAQueryFactory queryFactory;

    @BeforeEach
    public void before() {
        queryFactory = new JPAQueryFactory(em);

    }

    // 플랜테이어 전체 조회
    @Test
    public void plantria_read() {

        List<PostResponseDto> result = queryFactory
                .select(
                        Projections.constructor(PostResponseDto.class,
                                post.postId,
                                post.postTitle,
                                post.postImgUrl,
                                post.postContent,
                                user.nickname,
                                user.userId,
                                user.profileImageUrl))
                .from(post)
                .leftJoin(post.user, user)
                .where(
                        postTypeCode("postType01")
                        , postPlaceCode("pp01")
                )

                .fetch();
        for (PostResponseDto postResponseDto : result) {
            System.out.println("postResponseDto = " + postResponseDto);
        }
    }

    // 플랜테리어 통합검색 6개만 조회
    @Test
    public void integrateSearchPlanterior() {
        String keyword = "제목";
        List<PlantriaSearchResponseDto> result = queryFactory
                .select(
                        Projections.constructor(PlantriaSearchResponseDto.class,
                                post.postId,
                                post.postImgUrl
                        )
                )
                .from(post)
                .where(
                        searchKeyword(keyword).and(post.postType.postTypeCode.eq("postType01"))
                )
                .orderBy(post.createdAt.desc()).limit(6)
                .fetch();
        for (PlantriaSearchResponseDto plantriaSearchResponseDto : result) {
            System.out.println("plantriaSearchResponseDto = " + plantriaSearchResponseDto.getPostId());
        }

    }

    // 플랜테리어 통합검색 사진 - 전체 카운트
    @Test
    public void integrateSearchPlanteriorCount() {
        String keyword = "제목";
        long count = queryFactory
                .select(
                        Projections.constructor(PlantriaSearchResponseDto.class,
                                post.postId,
                                post.postImgUrl
                        )
                )
                .from(post)
                .where(
                        searchKeyword(keyword).and(post.postType.postTypeCode.eq("postType01"))
                )
                .orderBy(post.createdAt.desc())
                .fetchCount();
        System.out.println("count = " + count);

    }

    // 플렌테리어 통합검색 -식물도감
    @Test
    public void plantDictionaryList() {
        String keyword = "플";
        List<PlantImg> plantList = queryFactory
                .select(plantImg)
                .from(plantImg)
                .where(searchPlantNameKeyword(keyword))
                .orderBy(plantImg.plantName.asc())
                .fetch();
        for (PlantImg plantImg : plantList) {
            System.out.println("plantImg = " + plantImg.getPlantName());
        }
    }

    @Test
    public void plantDictionaryFilter() {
        String keyword = "엽";
        String plantPlaceCode = "pp01";
        String plantGrowthShapeCode = "pgs01";
        String plantTypeCode = "pt01";
        String plantLevelCode = "pl01";
        List<PlantDictionaryResponseDto> result = queryFactory
                .select(
                        Projections.constructor(PlantDictionaryResponseDto.class,
                                plantImg.plantNo,
                                plantImg.plantName,
                                plantImg.plantImgPrefix,
                                plantImg.plantImgName
                        )
                )
                .from(plant, plantImg)
                .where(
                        plant.plantNo.eq(plantImg.plantNo),
                        searchPlantNameKeyword(keyword),
                        containPlantPlace(plantPlaceCode),
                        containPlantGrowthShape(plantGrowthShapeCode),
                        containPlantType(plantTypeCode),
                        containPlantLevel(plantLevelCode)
                )
                .fetch();

        for (PlantDictionaryResponseDto plantDictionaryResponseDto : result) {
            System.out.println("plantNo = " + plantDictionaryResponseDto.getPlantNo());
            System.out.println("plantName = " + plantDictionaryResponseDto.getPlantName());
            System.out.println("PlantImgUrl = " + plantDictionaryResponseDto.getPlantImgUrl());

        }
        System.out.println("result.size() = " + result.size());
    }

    private BooleanExpression containPlantLevel(String plantLevelCode) {
        return plantLevelCode == null ? null : plant.plantLevelCode.contains(plantLevelCode);
    }

    private BooleanExpression containPlantType(String plantTypeCode) {
        return plantTypeCode == null ? null : plant.plantTypeCode.contains(plantTypeCode);
    }

    private BooleanExpression containPlantGrowthShape(String plantGrowthShapeCode) {
        return plantGrowthShapeCode == null ? null : plant.plantGrowthShapeCode.contains(plantGrowthShapeCode);
    }

    private BooleanExpression containPlantPlace(String plantPlaceCode) {
        return plantPlaceCode == null ? null : plant.plantPlaceCode.contains(plantPlaceCode);
    }


    // 커뮤니티 (초록톡 전체 조회)
    @Test
    public void chorokTalkList() {
        Long userId = 9L;
        Long postId = 32L;
        String postTypeCode = null;
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
                        post.postType.postTypeCode.notIn("postType01")
                                .and(coummityList(postTypeCode))
                )
                .fetch();
        for (CommunityResponseDto communityResponseDto : result) {
            System.out.println("communityResponseDto = " + communityResponseDto);
        }

    }

    // 커뮤니티 (초록톡 전체 조회) - 비로그인
    @Test
    public void non_login_chorokTalkList() {
        Long postId = 32L;
        String postTypeCode = null;
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
                                post.createdAt.as("postRecentTime")
                        )
                )
                .from(post)
                .where(
                        post.postType.postTypeCode.notIn("postType01")
                                .and(coummityList(postTypeCode))
                )
                .fetch();
        for (CommunityResponseDto communityResponseDto : result) {
            System.out.println("communityResponseDto = " + communityResponseDto);
        }

    }


    private BooleanExpression postBookChk(Long userId) {
        System.out.println(postBookMark.user.userId.eq(userId).count());
        if (postBookMark.user.userId.eq(userId) == null) {
            return Expressions.asBoolean(false);
        } else {
            return Expressions.asBoolean(true);
        }
    }

    private BooleanExpression postLikeChk(Long userId) {
        if (postLike.user.userId.eq(userId) == null) {
            return Expressions.asBoolean(false);
        } else {
            return Expressions.asBoolean(true);
        }
    }

    private BooleanExpression coummityList(String postTypeCode) {
        return postTypeCode == null ? post.postType.postTypeCode.in("postType02", "postType03", "postType04") : post.postType.postTypeCode.eq(postTypeCode);
    }

    private BooleanExpression searchPlantNameKeyword(String keyword) {
        return keyword == null ? null : plantImg.plantName.contains(keyword);
    }

    private BooleanExpression searchKeyword(String keyword) {
        return keyword == null ? null : post.postContent.contains(keyword).or(post.postTitle.contains(keyword));
    }

    private BooleanExpression postTypeCode(String postTypeCode) {
        return postTypeCode == null ? null : post.postType.postTypeCode.eq(postTypeCode);
    }

    private BooleanExpression postPlaceCode(String plantPlaceCode) {
        return plantPlaceCode == null ? null : post.plantPlaceCode.eq(plantPlaceCode);
    }


}