package com.finalproject.chorok.post.repository.querydsl;

import com.finalproject.chorok.mypage.dto.MyPlanteriorResponseDto;
import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.post.dto.*;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepositoryQueryDsl {
    // 1. 플렌테리어 전체 조회
    // 2. plantPlaceCode를 입력받으면 해당 장소로 필터링함
    Page<PostResponseDto> planteriorReadPosts(PlantriaFilterRequestDto postSearchRequestDto, Pageable pageable);

    // [플렌테리어 통합검색]
    // 1. 플랜테리어 통합 검색 - 사진 6개 조회
    List<PlantriaSearchResponseDto> integrateSearchPlanterior(PlantriaFilterRequestDto postSearchRequestDto);
    // 2. 플랜테리어 통합 검색 count
    Long integrateSearchPlanteriorCount(PlantriaFilterRequestDto postSearchRequestDto);
    // 3. 플랜테리어 통합 검색 - 식물도감
    List<PlantDictionaryResponseDto> planteriorDictionaryList(PlantriaFilterRequestDto postSearchRequestDto);
    List<PlantImg>  integratePlantDictionaryList(PlantriaFilterRequestDto postSearchRequestDto);
    // 4. 플랜테리어 통합 검색 - 식물도감 count
    Long plantDictionaryListCount(PlantriaFilterRequestDto postSearchRequestDto);

    // [플랜테리어 검색- 식물도감]
    Page<PlantDictionaryResponseDto> plantDictionaryList(DictionaryFilterDto dictionaryFilterDto,Pageable pageable);

    // [초록톡]
    // 초록톡 전체 게시물 조회(postTypeCode 필터링)-  로그인
    Page<CommunityResponseDto> chorokTalkList(Long userId, String postTypeCode,Pageable pageable);
    // 초록톡 전체 게시물 조회(postTypeCode 필터링) - 비로그인
    Page<CommunityResponseDto> non_login_chorokTalkList(String postTypeCode,Pageable pageable);

    // [마이페이지]
    // 내가 작성한 플랜테이어사진 전체 조회
    // 1. postTypeCode  = postType01 , -> plantPlaceCode로 필터링 가능
    // 2. postTypeCode != postType01 , -> plantPlaceCode로 필터링 불가능
    Page<CommunityResponseDto> myPlanterior(Long userId,PlantriaFilterRequestDto plantriaFilterRequestDto,Pageable pageable);
    // 내가 북마크한 게시물 전체 조회
    // 1. postTypeCode  = postType01 , -> plantPlaceCode로 필터링 가능
    // 2. postTypeCode != postType01 , -> plantPlaceCode로 필터링 불가능
    Page<CommunityResponseDto> myBookMarkPost(Long userId,PlantriaFilterRequestDto plantriaFilterRequestDto, Pageable pageable);


    // 내가 북마크한 식물
    Page<PlantDictionaryResponseDto> myPlantBookMark(Long userId, Pageable pageable);

    //1. 내가쓴 플렌테리어 카운트
    Long myPlanteriorCount(Long userId);
    //2. 내가쓴 플렌테리어 6개
    List<MyPlanteriorResponseDto> myPlanterior(Long userId);
    //3. 내가 북마크한 플렌테리어 카운트
    Long  myPlanteriorBookMarkCount(Long userId);
    //4. 내가 북마크한 플렌테리어 6개
    List<MyPlanteriorResponseDto> myPlanteriorBookMark(Long userId);



}
