package com.finalproject.chorok.post.repository.querydsl;

import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.post.dto.PlantriaFilterRequestDto;
import com.finalproject.chorok.post.dto.PlantriaSearchResponseDto;
import com.finalproject.chorok.post.dto.PostResponseDto;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.finalproject.chorok.login.model.QUser.user;
import static com.finalproject.chorok.plant.model.QPlantImg.plantImg;
import static com.finalproject.chorok.post.model.QPost.post;


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
                        postTypeCode(postSearchRequestDto.getPostTypeCode()),
                        postPlaceCode(postSearchRequestDto.getPlantPlaceCode()),
                        searchKeyword(postSearchRequestDto.getKeyword())
                )
                .orderBy(post.createdAt.desc())
                .fetch();
    }
    // 플렌태리어 - 통합검색 - 사진
    @Override
    public List<PlantriaSearchResponseDto> integrateSearchPlanterior(String keyword) {
       return queryFactory
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
    }
    // 플랜테리어 - 통합검색 - 사진갯수
    // 플랜테리어 - 검색(사진) 갯수
    @Override
    public Long integrateSearchPlanteriorCount(String keyword) {
        return queryFactory
                .select(
                        Projections.constructor(PlantriaSearchResponseDto.class,
                                post.postId,
                                post.postImgUrl
                        )
                )
                .from(post)
                .where(
                        searchKeyword(keyword)
                                .and(post.postType.postTypeCode.eq("postType01"))
                )
                .fetchCount();
    }
    // 플랜테리어 - 통합검색 - 식물도감
    @Override
    public List<PlantImg> plantDictionaryList(String keyword) {
        return queryFactory
                .select(plantImg)
                .from(plantImg)
                .where(searchPlantNameKeyword(keyword))
                .orderBy(plantImg.plantName.asc())
                .limit(2)
                .fetch();
    }
    // 플랜테리어 - 통합검색 - 식물도감갯수
    @Override
    public Long plantDictionaryListCount(String keyword) {
        return queryFactory
                .select(plantImg)
                .from(plantImg)
                .where(searchPlantNameKeyword(keyword))
                .limit(2)
                .fetchCount();
    }

    // 플랜테리어 - 통합검색(사진)
//    @Override
//    public List<PlantriaSearchResponseDto> photoSearchPlanterior(String keyword) {
//        return queryFactory
//                .select(
//                        Projections.constructor(PlantriaSearchResponseDto.class,
//                                post.postId,
//                                post.postImgUrl
//                        )
//                )
//                .from(post)
//                .where(
//                        searchKeyword(keyword).and(post.postType.postTypeCode.eq("postType01"))
//                )
//                .orderBy(post.createdAt.desc()).limit(6)
//                .fetch();
//    }

    /* 공통  */
    private BooleanExpression postTypeCode(String postTypeCode) {
        return postTypeCode == null ? null : post.postType.postTypeCode.eq(postTypeCode);
    }
    private Predicate postPlaceCode(String plantPlaceCode) {
        return plantPlaceCode == null ? null : post.plantPlaceCode.eq(plantPlaceCode);
    }
    private BooleanExpression searchKeyword(String keyword) {
        return keyword == null ? null : post.postContent.contains(keyword).or(post.postTitle.contains(keyword));
    }
    private BooleanExpression searchPlantNameKeyword(String keyword) {
        return keyword ==null ? null : plantImg.plantName.contains(keyword);
    }


}
