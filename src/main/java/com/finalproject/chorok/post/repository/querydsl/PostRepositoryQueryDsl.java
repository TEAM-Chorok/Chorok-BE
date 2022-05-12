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

    // [플렌테리어 통합검색]
    // 1. 플랜테리어 통합 검색 - 사진 6개 조회
    List<PlantriaSearchResponseDto> integrateSearchPlanterior(String keyword);
    // 2. 플랜테리어 통합 검색 count
    Long integrateSearchPlanteriorCount(String keyword);
    // 3. 플랜테리어 통합 검색 - 식물도감
    List<PlantImg> plantDictionaryList(String keyword);
    // 4. 플랜테리어 통합 검색 - 식물도감 count
    Long plantDictionaryListCount(String keyword);
    // [플랜테리어 검색- 식물도감]

    // 초록톡 전체 게시물 조회(postTypeCode 필터링)

}
