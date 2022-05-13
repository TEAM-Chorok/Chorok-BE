package com.finalproject.chorok.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class DictionaryFilterDto {
    // 검색키워드
    private String keyword;
    // 식물 관리 레벨
    private String plantLevelCode;
    // 식물 장소
    private String plantPlaceCode;
    // 식물 종류
    private String plantTypeCode;
    // 생육 형태
    private String plantGrowthShapeCode;


}
