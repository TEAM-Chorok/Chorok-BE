package com.finalproject.chorok.post.repository.querydsl;

import com.finalproject.chorok.mypage.dto.MyPlanteriorResponseDto;
import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.post.dto.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.List;

import static com.finalproject.chorok.login.model.QUser.user;
import static com.finalproject.chorok.mypage.model.QPlantBookMark.plantBookMark;
import static com.finalproject.chorok.plant.model.QPlant.plant;
import static com.finalproject.chorok.plant.model.QPlantImg.plantImg;
import static com.finalproject.chorok.plant.model.QPlantPlace.plantPlace1;
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
    public Page<PostResponseDto> planteriorReadPosts(PlantriaFilterRequestDto postSearchRequestDto, Pageable pageable) {
        QueryResults<PostResponseDto> results = queryFactory
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
                        post.postType.postTypeCode.eq("postType01"),
                        //postTypeCode(postSearchRequestDto.getPostTypeCode()),
                        plantPlaceCode(postSearchRequestDto.getPlantPlaceCode()),
                        searchKeyword(postSearchRequestDto.getKeyword())
                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PostResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
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
                        plantPlaceCode(postSearchRequestDto.getPlantPlaceCode())
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
    public Page<PlantDictionaryResponseDto> plantDictionaryList(DictionaryFilterDto dictionaryFilterDto,Pageable pageable) {
        QueryResults<PlantDictionaryResponseDto> results = queryFactory
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
                .orderBy(plantImg.plantName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<PlantDictionaryResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }
    //플랜테리어 - 통합검색 - 식물도감
    @Override
    public List<PlantDictionaryResponseDto> planteriorDictionaryList(PlantriaFilterRequestDto postSearchRequestDto) {
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
                        searchPlantNameKeyword(postSearchRequestDto.getKeyword())
                )
                .orderBy(plantImg.plantName.asc()).limit(2)
                .fetch();
    }


    // 초록톡 전체게시물 조회 - 로그인시
    @Override
    public Page<CommunityResponseDto> chorokTalkList(Long userId, String postTypeCode,Pageable pageable) {
        QueryResults<CommunityResponseDto> results = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postTitle,
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
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommunityResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
    // 초록톡 전체게시물 조회 - 비로그인시
    @Override
    public Page<CommunityResponseDto> non_login_chorokTalkList(String postTypeCode,Pageable pageable) {
        QueryResults<CommunityResponseDto> results = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postTitle,
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
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<CommunityResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
    // [마이페이지]
    // 내가 작성한 플렌테리어 - 전체 조회
    @Override
    public Page<CommunityResponseDto> myPlanterior(Long userId, PlantriaFilterRequestDto plantriaFilterRequestDto,Pageable pageable) {
        QueryResults<CommunityResponseDto> results = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postTitle,
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
                                post.createdAt.as("postRecentTime"),
                        ExpressionUtils.as(
                                JPAExpressions
                                        .select(plantPlace1.plantPlace)
                                        .from(plantPlace1)
                                        // .leftJoin(post).on(post.plantPlaceCode.eq(plantPlace1.plantPlaceCode))
                                        .where(
                                                post.plantPlaceCode.eq(plantPlace1.plantPlaceCode)
                                        ), "plantPlace")

                        )
                )
                .from(post)
                .where(
                        post.user.userId.eq(userId),
                        postTypeCode(plantriaFilterRequestDto.getPostTypeCode()),
                        plantPlaceCode(plantriaFilterRequestDto.getPlantPlaceCode())

                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<CommunityResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    // 내가 북마크한 게시물 전체 조회
    @Override
    public Page<CommunityResponseDto> myBookMarkPost(Long userId, PlantriaFilterRequestDto plantriaFilterRequestDto,Pageable pageable) {
        QueryResults<CommunityResponseDto> results = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postTitle,
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
                                post.createdAt.as("postRecentTime"),
                                ExpressionUtils.as(
                                        JPAExpressions
                                                .select(plantPlace1.plantPlace)
                                                .from(plantPlace1)
                                                .where(post.plantPlaceCode.eq(plantPlace1.plantPlaceCode)
                                                ),"plantPlace")

                        )
                )
                .from(post)
                .leftJoin(postBookMark).on(post.postId.eq(postBookMark.post.postId))
                .where(
                        postBookMark.user.userId.eq(userId),
                        postTypeCode(plantriaFilterRequestDto.getPostTypeCode()),
                        plantPlaceCode(plantriaFilterRequestDto.getPlantPlaceCode())

                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<CommunityResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }

    // 내가 북마크한 커뮤니티 전체조회
    // 2022-05-23 북마크 쿼리 추기
    @Override
    public Page<CommunityResponseDto> myCommunityBookMark(Long userId, Pageable pageable) {
        QueryResults<CommunityResponseDto> results = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postTitle,
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
                .leftJoin(postBookMark).on(post.postId.eq(postBookMark.post.postId))
                .where(
                        postBookMark.user.userId.eq(userId),
                       // post.user.userId.eq(userId),
                        post.postType.postTypeCode.notIn("postType01")

                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<CommunityResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }
    // 내가 쓴 커뮤니티 게시물 전체 조회
    @Override
    public Page<CommunityResponseDto> myCommunity(Long userId, Pageable pageable) {
        QueryResults<CommunityResponseDto> results = queryFactory
                .select(Projections.constructor(
                                CommunityResponseDto.class,
                                post.postId,
                                post.user.nickname,
                                post.user.profileImageUrl.as("profileImgUrl"),
                                post.postTitle,
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
                        post.postType.postTypeCode.notIn("postType01")

                )
                .orderBy(post.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        List<CommunityResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);
    }


    // 내가 북마크한 식물
    @Override
    public Page<PlantDictionaryResponseDto> myPlantBookMark(Long userId, Pageable pageable) {
        QueryResults<PlantDictionaryResponseDto> results = queryFactory
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
                .orderBy(plant.plantName.asc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<PlantDictionaryResponseDto> content = results.getResults();
        long total = results.getTotal();

        return new PageImpl<>(content, pageable, total);

    }
    //1. 내가쓴 플렌테리어 카운트
    @Override
    public Long myPlanteriorCount(Long userId) {
        return queryFactory
                .selectFrom(post)
                .where(
                        post.user.userId.eq(userId),
                        post.postType.postTypeCode.eq("postType01")
                )
                .fetchCount();
    }
    //2. 내가쓴 플렌테리어 6개
    @Override
    public List<MyPlanteriorResponseDto> myPlanterior(Long userId) {
        return queryFactory
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
    }
    //3. 내가 북마크한 플렌테리어 카운트
    @Override
    public Long myPlanteriorBookMarkCount(Long userId) {
        return queryFactory
                .selectFrom(post)
                .leftJoin(postBookMark)
                .on(post.postId.eq(postBookMark.post.postId))
                .where(
                        post.postType.postTypeCode.eq("postType01"),
                        postBookMark.user.userId.eq(userId)
                )
                .orderBy(post.createdAt.desc())
                .fetchCount();
    }
    //4. 내가 북마크한 플렌테리어 6개
    @Override
    public List<MyPlanteriorResponseDto> myPlanteriorBookMark(Long userId) {
        return queryFactory
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
    }


    /* 공통  */
    private BooleanExpression communityList(String postTypeCode) {
        return postTypeCode == null  ? post.postType.postTypeCode.in("postType02", "postType03", "postType04") : post.postType.postTypeCode.eq(postTypeCode);
    }
    private BooleanExpression postTypeCode(String postTypeCode) {
        return postTypeCode == null ? null : post.postType.postTypeCode.eq(postTypeCode);
    }
    private BooleanExpression plantPlaceCode(String plantPlaceCode) {
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
