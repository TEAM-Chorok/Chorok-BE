package com.finalproject.chorok.post.repository.querydsl;

import com.finalproject.chorok.plant.model.PlantImg;
import com.finalproject.chorok.post.dto.PlantriaFilterRequestDto;
import com.finalproject.chorok.post.dto.PlantriaSearchResponseDto;
import com.finalproject.chorok.post.dto.PostResponseDto;

import java.util.List;

public interface PostRepositoryQueryDsl {
    // 1. 플렌테리어 전체 조회
    // 2. plantPlaceCode를 입력받으면 해당 장소로 필터링함
    List<PostResponseDto> plantriaReadPosts(PlantriaFilterRequestDto postSearchRequestDto);

    // 1. 플랜테리어 통합 검색 - 사진 6개 조회
    List<PlantriaSearchResponseDto> integrateSearchPlanterior(String keyword);
    Long integrateSearchPlanteriorCount(String keyword);
    List<PlantImg> plantDictionaryList(String keyword);
    Long plantDictionaryListCount(String keyword);
    List<PlantriaSearchResponseDto> photoSearchPlanterior(String keyword);
}
