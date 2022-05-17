package com.finalproject.chorok.post.repository.querydsl;

import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.post.dto.*;

import java.util.List;

public interface PostRepositoryQueryDsl {
    // 1. 플렌테리어 전체 조회
    // 2. plantPlaceCode를 입력받으면 해당 장소로 필터링함
    List<PostResponseDto> planteriorReadPosts(PlantriaFilterRequestDto postSearchRequestDto);

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
    List<PlantDictionaryResponseDto> plantDictionaryList(DictionaryFilterDto dictionaryFilterDto);

    // [초록톡]
    // 초록톡 전체 게시물 조회(postTypeCode 필터링)-  로그인
    List<CommunityResponseDto> chorokTalkList(Long userId, String postTypeCode);

    // 초록톡 전체 게시물 조회(postTypeCode 필터링) - 비로그인
    List<CommunityResponseDto> non_login_chorokTalkList(String postTypeCode);
}
