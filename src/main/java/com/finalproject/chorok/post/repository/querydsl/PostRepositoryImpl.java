package com.finalproject.chorok.post.repository.querydsl;

import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.post.dto.*;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.finalproject.chorok.login.model.QUser.user;
import static com.finalproject.chorok.plant.model.QPlant.plant;
import static com.finalproject.chorok.plant.model.QPlantImg.plantImg;
import static com.finalproject.chorok.post.model.QComment.comment;
import static com.finalproject.chorok.post.model.QPost.post;
import static com.finalproject.chorok.post.model.QPostBookMark.postBookMark;
import static com.finalproject.chorok.post.model.QPostLike.postLike;


public class PostRepositoryImpl implements PostRepositoryQueryDsl{

    private final JPAQueryFactory queryFactory;

    public PostRepositoryImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);

    }

    // 플랜테리어 전체조회(postType01,plantPlaceCode,keyword)
    @Override
    public List<PostResponseDto> plantriaReadPosts(PlantriaFilterRequestDto postSearchRequestDto) {
        return queryFactory
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
                        postTypeCode("postType01"),
                        postPlaceCode(postSearchRequestDto.getPlantPlaceCode()),
                        searchKeyword(postSearchRequestDto.getKeyword())
                )
                .orderBy(post.createdAt.desc())
                .fetch();
    }
    // 플렌태리어 - 통합검색 - 사진
    @Override
    public List<PlantriaSearchResponseDto> integrateSearchPlanterior(PlantriaFilterRequestDto postSearchRequestDto) {
       return queryFactory
                .select(
                        Projections.constructor(PlantriaSearchResponseDto.class,
                                post.postId,
                                post.postImgUrl
                        )
                )
                .from(post)
                .where(
                        post.postType.postTypeCode.eq("postType01"),
                        searchKeyword(postSearchRequestDto.getKeyword())
                )
                .orderBy(post.createdAt.desc()).limit(6)
                .fetch();
    }
    // 플랜테리어 - 통합검색 - 사진갯수
    // 플랜테리어 - 검색(사진) 갯수
    @Override
    public Long integrateSearchPlanteriorCount(PlantriaFilterRequestDto postSearchRequestDto) {
        return queryFactory
                .select(
                        Projections.constructor(PlantriaSearchResponseDto.class,
                                post.postId,
                                post.postImgUrl
                        )
                )
                .from(post)
                .where(
                                post.postType.postTypeCode.eq("postType01"),
                                searchKeyword(postSearchRequestDto.getKeyword()),
                                postPlaceCode(postSearchRequestDto.getPlantPlaceCode())
                )
                .fetchCount();
    }
    // 플랜테리어 - 통합검색 - 식물도감
    @Override
    public List<PlantImg> integratePlantDictionaryList(PlantriaFilterRequestDto postSearchRequestDto) {
        return queryFactory
                .select(plantImg)
                .from(plantImg)
                .where(searchPlantNameKeyword(postSearchRequestDto.getKeyword()))
                .orderBy(plantImg.plantName.asc())
                .limit(2)
                .fetch();
    }
    // 플랜테리어 - 통합검색 - 식물도감갯수
    @Override
    public Long plantDictionaryListCount(PlantriaFilterRequestDto postSearchRequestDto) {
        return queryFactory
                .select(plantImg)
                .from(plantImg)
                .where(searchPlantNameKeyword(postSearchRequestDto.getKeyword()))
                .limit(2)
                .fetchCount();
    }
    //플랜테리어 - 식물도감
    @Override
    public List<PlantDictionaryResponseDto> plantDictionaryList(DictionaryFilterDto dictionaryFilterDto) {
        return queryFactory
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
                        searchPlantNameKeyword(dictionaryFilterDto.getKeyword()),
                        containPlantPlace(dictionaryFilterDto.getPlantPlaceCode()),
                        containPlantGrowthShape(dictionaryFilterDto.getPlantGrowthShapeCode()),
                        containPlantType(dictionaryFilterDto.getPlantTypeCode()),
                        containPlantLevel(dictionaryFilterDto.getPlantLevelCode())
                )
                .fetch();
    }



    // 초록톡 전체게시물 조회 - 로그인시
    @Override
    public List<CommunityResponseDto> chorokTalkList(Long userId, String postTypeCode) {
        return queryFactory
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
                                .and(communityList(postTypeCode)
                              //  ,searchPlantNameKeyword(keyword)
                                        )

                )
                .fetch();
    }
    // 초록톡 전체게시물 조회 - 비로그인시
    @Override
    public List<CommunityResponseDto> non_login_chorokTalkList(String postTypeCode) {
        return queryFactory
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
                                .and(communityList(postTypeCode))
                )
                .fetch();
    }


    /* 공통  */
    private BooleanExpression communityList(String postTypeCode) {
        return postTypeCode == null ? post.postType.postTypeCode.in("postType02", "postType03", "postType04") : post.postType.postTypeCode.eq(postTypeCode);
    }
    private BooleanExpression postTypeCode(String postTypeCode) {
        return postTypeCode == null ? null : post.postType.postTypeCode.eq(postTypeCode);
    }
    private BooleanExpression postPlaceCode(String plantPlaceCode) {
        return plantPlaceCode == null ? null : post.plantPlaceCode.eq(plantPlaceCode);
    }
    private BooleanExpression searchKeyword(String keyword) {
        return keyword == null ? null : post.postContent.contains(keyword).or(post.postTitle.contains(keyword));
    }
    private BooleanExpression searchPlantNameKeyword(String keyword) {
        return keyword ==null ? null : plantImg.plantName.contains(keyword);
    }
    // 필터 검색(contains)
    private BooleanExpression containPlantLevel(String plantLevelCode) {
        return plantLevelCode == null?null:plant.plantLevelCode.contains(plantLevelCode);
    }

    private BooleanExpression containPlantType(String plantTypeCode) {
        return plantTypeCode == null?null : plant.plantTypeCode.contains(plantTypeCode);
    }

    private BooleanExpression containPlantGrowthShape(String plantGrowthShapeCode) {
        return plantGrowthShapeCode == null?null:plant.plantGrowthShapeCode.contains(plantGrowthShapeCode);
    }

    private BooleanExpression containPlantPlace(String plantPlaceCode) {
        return plantPlaceCode==null?null: plant.plantPlaceCode.contains(plantPlaceCode);
    }


}
